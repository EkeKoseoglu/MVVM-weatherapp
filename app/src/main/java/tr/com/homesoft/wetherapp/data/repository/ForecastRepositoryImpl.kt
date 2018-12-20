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
import tr.com.homesoft.wetherapp.data.local.entity.Location
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.provider.LocationProvider
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.remote.WeatherNetworkDataSource
import tr.com.homesoft.wetherapp.data.remote.response.WeatherResponse
import tr.com.homesoft.wetherapp.ui.view.UnitSystem
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weeklyWeatherDao: WeeklyWeatherDao,
    private val locationDao: LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    //private val unitProvider: UnitProvider,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    /*
  private val metric: Boolean
      get() = unitProvider.getUnitSystem() == UnitSystem.METRIC
  */

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override fun getCurrentWeather(isMetric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry> {
        val current = MediatorLiveData<UnitSpecificCurrentWeatherEntry>()
        CoroutineScope(Dispatchers.Main).launch {
            val unitSpecificData = withContext(Dispatchers.IO) {
                initWeatherData()
                /*
                if (metric) currentWeatherDao.weatherMetric
                else currentWeatherDao.weatherImperial
                */
                currentWeatherDao.weatherMetric
            }
            current.removeSource(unitSpecificData)
            current.addSource(unitSpecificData) { current.value = (it) }
        }
        return current
    }



    override fun getWeatherByDate(date: String): LiveData<out UnitSpecificWeeklyForecastEntry> {
        val weatherByDate = MediatorLiveData<UnitSpecificWeeklyForecastEntry>()
        CoroutineScope(Dispatchers.Main).launch {
            val data = withContext(Dispatchers.IO) {
                with(weeklyWeatherDao) {
                    //if (metric) findMetricByDate(date) else findImperialByDate(date)
                    findImperialByDate(date)
                }
            }
            weatherByDate.removeSource(data)
            weatherByDate.addSource(data) {weatherByDate.value = it}
        }
        return weatherByDate
    }
/*
    override suspend fun getWeatherByDate(
        date: String
    ): LiveData<out UnitSpecificWeeklyForecastEntry> =
        withContext(Dispatchers.IO) {
            return@withContext if (metric) weeklyWeatherDao.findMetricByDate(date) else weeklyWeatherDao.findImperialByDate(
                date
            )
        }
*/

    /*
    override suspend fun getCurrentWeather() =
        withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.weatherMetric else currentWeatherDao.weatherImperial
        }
    */
    /*
    override suspend fun getWeeklyWeather() =
        withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) weeklyWeatherDao.metricWeeklyForecast else weeklyWeatherDao.imperialWeatherForecast
        }
    */

    override val weeklyWeather: LiveData<out List<UnitSpecificWeeklyForecastEntry>>
        get() {
            val weekly = MediatorLiveData<List<UnitSpecificWeeklyForecastEntry>>()
            CoroutineScope(Dispatchers.Main).launch {
                val unitSpecificData = withContext(Dispatchers.IO) {
                    initWeatherData()
                    with(weeklyWeatherDao) {
                        //if (metric) metricWeeklyForecast else imperialWeatherForecast
                        metricWeeklyForecast
                    }
                }
                weekly.removeSource(unitSpecificData)
                weekly.addSource(unitSpecificData) {weekly.value = it}
            }

            return weekly
        }

    override val currentWeather: LiveData< UnitSpecificCurrentWeatherEntry>
        get() {
            val current = MediatorLiveData<UnitSpecificCurrentWeatherEntry>()
            CoroutineScope(Dispatchers.Main).launch {
                val unitSpecificData = withContext(Dispatchers.IO) {
                    initWeatherData()
                    /*
                    if (metric) currentWeatherDao.weatherMetric
                    else currentWeatherDao.weatherImperial
                    */
                    currentWeatherDao.weatherMetric
                }
                current.removeSource(unitSpecificData)
                current.addSource(unitSpecificData) { current.value = (it) }
            }
            return current
        }


    override val location: LiveData<Location>
        get() {
            val loc = MediatorLiveData<Location>()
            CoroutineScope(Dispatchers.Main).launch {
                val locationData = withContext(Dispatchers.IO) {locationDao.weatherLocation}
                loc.removeSource(locationData)
                loc.addSource(locationData) {
                    loc.value = (it)
                }
            }
            return loc
        }

/*
    override suspend fun getLocation() =
        withContext(Dispatchers.IO) {
            return@withContext locationDao.weatherLocation
        }
*/

    private fun persistFetchedCurrentWeather(fetchedCurrentWeather: WeatherResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            locationDao.upsert(fetchedCurrentWeather.location)
            currentWeatherDao.upsert(fetchedCurrentWeather.currentWeatherEntry)
            weeklyWeatherDao.deleteAll()
            weeklyWeatherDao.upsertAll(*fetchedCurrentWeather.forecast.weeklyForcastList.toTypedArray())
        }
    }


    private suspend fun initWeatherData() {
        val lastLocation = locationDao.weatherLocation.value

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