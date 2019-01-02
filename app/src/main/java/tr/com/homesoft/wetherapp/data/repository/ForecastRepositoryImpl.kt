package tr.com.homesoft.wetherapp.data.repository

import androidx.lifecycle.LiveData
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

    private var lastWeatherLocation: WeatherLocation? = null

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
            lastWeatherLocation = newCurrentWeather.weatherLocation
        }
    }


    override suspend fun getDetailWeatherByDate(
        date: String,
        isMetric: Boolean
    ): LiveData<out UnitSpecificWeeklyForecastEntry> =

        withContext(Dispatchers.IO) {
            with(weeklyWeatherDao) {
                if (isMetric) findMetricByDate(date) else findImperialByDate(date)
            }
        }


    override suspend fun getWeeklyWeather(isMetric: Boolean): LiveData<out List<UnitSpecificWeeklyForecastEntry>> =

        withContext(Dispatchers.IO) {
            //Fetch data from the Internet
            initWeatherData()

            //Load persisted data from db

            with(weeklyWeatherDao) {
                if (isMetric) metricWeeklyForecast
                else imperialWeatherForecast
            }
        }


    override suspend fun getCurrentWeather(isMetric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> =
        withContext(Dispatchers.IO) {
            initWeatherData()
            with(currentWeatherDao) {
                if (isMetric) weatherMetric
                else weatherImperial
            }
        }


    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> =
        withContext(Dispatchers.IO) {
            return@withContext locationDao.weatherLocation
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

        //If last found location is null or device location is different than the last found location
        //fetch data from the Internet and return from the function
        if (null == lastWeatherLocation ||
            locationProvider.hasLocationChanged(lastWeatherLocation!!)
        ) {
            fetchCurrentWeather()
            return
        }

        //Check if fetching of data is needed
        //If needed, fetch data from the Internet
        if (isFetchCurrentNeeded(lastWeatherLocation!!.zonedDateTime))
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