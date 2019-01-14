package tr.com.homesoft.weatherapp.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import tr.com.homesoft.weatherapp.data.local.dao.CurrentWeatherDao
import tr.com.homesoft.weatherapp.data.local.dao.LocationDao
import tr.com.homesoft.weatherapp.data.local.dao.WeeklyWeatherDao
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.weatherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.weatherapp.data.provider.LocationProvider
import tr.com.homesoft.weatherapp.data.remote.WeatherNetworkDataSource
import tr.com.homesoft.weatherapp.data.remote.response.WeatherResponse
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weeklyWeatherDao: WeeklyWeatherDao,
    private val locationDao: LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    private lateinit var lastWeatherLocation: WeatherLocation

    init {
        weatherNetworkDataSource.downloadedWeather.observeForever {
            persistFetchedWeather(it)
            lastWeatherLocation = it.weatherLocation
        }

        locationProvider.locationLiveData.observeForever {
            CoroutineScope(Dispatchers.IO).launch {
                initWeatherData()
            }
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
            //Load persisted data from db
            with(weeklyWeatherDao) {
                if (isMetric) metricWeeklyForecast
                else imperialWeatherForecast
            }
        }


    override suspend fun getCurrentWeather(isMetric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> =
        withContext(Dispatchers.IO) {
            with(currentWeatherDao) {
                if (isMetric) weatherMetric
                else weatherImperial
            }
        }


    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> =
        withContext(Dispatchers.IO) {
            locationDao.weatherLocation
        }


    private fun persistFetchedWeather(fetchedCurrentWeather: WeatherResponse) {
        //Persist Fetched Data from Internet
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.upsert(fetchedCurrentWeather.weatherLocation)
            currentWeatherDao.upsert(fetchedCurrentWeather.currentWeatherEntry)
            weeklyWeatherDao.deleteAll()
            weeklyWeatherDao.upsertAll(*fetchedCurrentWeather.forecast.weeklyForecastList.toTypedArray())
        }
    }


    private suspend fun initWeatherData() {

        //If last found location, which is a lateinit var, is not initialized or device's location is different than
        // the last found location, fetch data from the Internet and return from the function

        if (::lastWeatherLocation.isInitialized.not() || locationProvider.hasLocationChanged(lastWeatherLocation)
        ) {
            fetchWeather()
            return
        }

        //Check if fetching of data is needed
        //If needed, fetch data from the Internet
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchWeather()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private suspend fun fetchWeather() {
        //Fetch data from the Internet
        weatherNetworkDataSource.fetchWeather(
            locationProvider.getPreferredLocation(),
            Locale.getDefault().language
        )
    }

}