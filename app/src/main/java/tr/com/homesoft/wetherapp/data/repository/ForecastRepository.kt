package tr.com.homesoft.wetherapp.data.repository

import androidx.lifecycle.LiveData
import tr.com.homesoft.wetherapp.data.local.entity.Location
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry

interface ForecastRepository {

    fun getCurrentWeather(isMetric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry>

    val location: LiveData<Location>

    val currentWeather: LiveData<out UnitSpecificCurrentWeatherEntry>

    val weeklyWeather: LiveData<out List<UnitSpecificWeeklyForecastEntry>>

    fun getWeatherByDate(date: String): LiveData<out UnitSpecificWeeklyForecastEntry>

    //suspend fun getLocation(): LiveData<Location>

    //suspend fun getCurrentWeather(): LiveData<out UnitSpecificCurrentWeatherEntry>

    //suspend fun getWeeklyWeather(): LiveData<out List<UnitSpecificWeeklyForecastEntry>>

    //suspend fun getWeatherByDate(date: String): LiveData<out UnitSpecificWeeklyForecastEntry>
}