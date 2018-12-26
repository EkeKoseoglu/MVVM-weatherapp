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
            mDetailForecast.removeSource(data)
            mDetailForecast.addSource(data, mDetailForecast::setValue)
        }
        return mDetailForecast
    }

    private val mWeeklyWeather = MediatorLiveData<List<UnitSpecificWeeklyForecastEntry>>()

    override fun getWeeklyWeather(isMetric: Boolean): LiveData<List<UnitSpecificWeeklyForecastEntry>> {

        CoroutineScope(Dispatchers.Main).launch {
            val unitSpecificData = withContext(Dispatchers.IO) {
                initWeatherData()
                with(weeklyWeatherDao) {
                    if (isMetric) metricWeeklyForecast else imperialWeatherForecast
                }
            }
            mWeeklyWeather.removeSource(unitSpecificData)
            mWeeklyWeather.addSource(unitSpecificData, mWeeklyWeather::setValue)
        }
        return mWeeklyWeather
    }

    private val mCurrentWeather = MediatorLiveData<UnitSpecificCurrentWeatherEntry>()

    override fun getCurrentWeather(isMetric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry> {

        CoroutineScope(Dispatchers.Main).launch {
            val unitSpecificData = withContext(Dispatchers.IO) {
                initWeatherData()
                if (isMetric) currentWeatherDao.weatherMetric
                else currentWeatherDao.weatherImperial
            }
            mCurrentWeather.removeSource(unitSpecificData)
            mCurrentWeather.addSource(unitSpecificData, mCurrentWeather::setValue)

        }
        return mCurrentWeather
    }

    private val mWeatherLocation = MediatorLiveData<WeatherLocation>()

    override val weatherLocation: LiveData<WeatherLocation>
        get() {
            CoroutineScope(Dispatchers.Main).launch {
                val locationData = withContext(Dispatchers.IO) { locationDao.weatherLocation }
                mWeatherLocation.addSource(locationData, mWeatherLocation::setValue)
            }
            return mWeatherLocation
        }


    private fun persistFetchedCurrentWeather(fetchedCurrentWeather: WeatherResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.upsert(fetchedCurrentWeather.weatherLocation)
            currentWeatherDao.upsert(fetchedCurrentWeather.currentWeatherEntry)
            weeklyWeatherDao.deleteAll()
            weeklyWeatherDao.upsertAll(*fetchedCurrentWeather.forecast.weeklyForcastList.toTypedArray())
        }
    }


    private suspend fun initWeatherData() {
        val lastLocation = mWeatherLocation.value

        if (null == lastLocation || locationProvider.hasLocationChanged(lastLocation)) {
            fetchCurrentWeather()
            return
        }
        if (isFetchCurrentNeeded(lastLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchCurrentWeather() {

        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocation(),
            Locale.getDefault().language
        )
    }


}