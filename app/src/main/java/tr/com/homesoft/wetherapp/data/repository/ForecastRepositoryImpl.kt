package tr.com.homesoft.wetherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import tr.com.homesoft.wetherapp.data.local.dao.CurrentWeatherDao
import tr.com.homesoft.wetherapp.data.local.dao.LocationDao
import tr.com.homesoft.wetherapp.data.local.dao.WeeklyWeatherDao
import tr.com.homesoft.wetherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.provider.LocationProvider
import tr.com.homesoft.wetherapp.data.remote.WeatherNetworkDataSource
import tr.com.homesoft.wetherapp.data.remote.response.WeatherResponse
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weeklyWeatherDao: WeeklyWeatherDao,
    private val locationDao: LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    private val mDetailForecast = MediatorLiveData<UnitSpecificWeeklyForecastEntry>()

    override fun getDetailWeatherByDate(date: String, isMetric: Boolean): LiveData<UnitSpecificWeeklyForecastEntry> {

        CoroutineScope(Dispatchers.Main).launch {
            val data = withContext(Dispatchers.IO) {
                with(weeklyWeatherDao) {
                    if (isMetric) findMetricByDate(date) else findImperialByDate(date)
                }
            }
            mDetailForecast.addSource(data, mDetailForecast::setValue)
        }
        return mDetailForecast
    }

    private val mWeeklyWeather = MediatorLiveData<List<UnitSpecificWeeklyForecastEntry>>()

    override fun getWeeklyWeather(isMetric: Boolean): LiveData<List<UnitSpecificWeeklyForecastEntry>> {

        CoroutineScope(Dispatchers.IO).launch {
            //Fetch data from the Internet
            initWeatherData()

            //Load persisted data from db
            val unitSpecificData =
                with(weeklyWeatherDao) {
                    if (isMetric) metricWeeklyForecast
                    else imperialWeatherForecast
                }

            //Observe data changes on the Main thread
            withContext(Dispatchers.Main) {
                mWeeklyWeather.addSource(unitSpecificData, mWeeklyWeather::setValue)
            }
        }
        return mWeeklyWeather
    }

    private val mCurrentWeather = MediatorLiveData<UnitSpecificCurrentWeatherEntry>()

    override fun getCurrentWeather(isMetric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry> {

        CoroutineScope(Dispatchers.IO).launch {
            //Fetch data from the Internet
            initWeatherData()

            //Load persisted data from db
            val unitSpecificData =
                with(currentWeatherDao) {
                    if (isMetric) weatherMetric
                    else weatherImperial
                }

            //Observe data changes on the Main thread
            withContext(Dispatchers.Main) { mCurrentWeather.addSource(unitSpecificData, mCurrentWeather::setValue) }
        }
        return mCurrentWeather
    }

    private val mWeatherLocation = MediatorLiveData<WeatherLocation>()

    override val weatherLocation: LiveData<WeatherLocation>
        get() {
            CoroutineScope(Dispatchers.IO).launch {

                //Load persisted data from db
                val locationData = locationDao.weatherLocation

                //Observe data changes on the Main thread
                withContext(Dispatchers.Main) { mWeatherLocation.addSource(locationData, mWeatherLocation::setValue) }
            }
            return mWeatherLocation
        }


    private fun persistFetchedCurrentWeather(fetchedCurrentWeather: WeatherResponse) {
        //Persist Fetched Data from Internet
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.upsert(fetchedCurrentWeather.weatherLocation)
            currentWeatherDao.upsert(fetchedCurrentWeather.currentWeatherEntry)
            weeklyWeatherDao.deleteAll()
            weeklyWeatherDao.upsertAll(*fetchedCurrentWeather.forecast.weeklyForcastList.toTypedArray())
        }
    }


    private suspend fun initWeatherData() {
        //Get Last Found Location
        val lastLocation = mWeatherLocation.value

        //If last found location is null or device location is different than the last found location
        //fetch data from the Internet and return from the function
        if (null == lastLocation || locationProvider.hasLocationChanged(lastLocation)) {
            fetchCurrentWeather()
            return
        }

        //Check if fetching of data is needed
        //If needed, fetch data from the Internet
        if (isFetchCurrentNeeded(lastLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchCurrentWeather() {
        //Fetch data from the Internet
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocation(),
            Locale.getDefault().language
        )
    }


}