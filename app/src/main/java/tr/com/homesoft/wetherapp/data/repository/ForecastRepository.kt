package tr.com.homesoft.wetherapp.data.repository

import androidx.lifecycle.LiveData
import tr.com.homesoft.wetherapp.data.local.entity.Location
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry

interface ForecastRepository {

    val location: LiveData<Location>

    val currentWeather: LiveData< UnitSpecificCurrentWeatherEntry>

    val weeklyWeather: LiveData<out List<UnitSpecificWeeklyForecastEntry>>

    fun getWeatherByDate(date: String): LiveData<out UnitSpecificWeeklyForecastEntry>

    fun getCurrentWeather(isMetric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry>

    //suspend fun getLocation(): LiveData<Location>

    //suspend fun getCurrentWeather(): LiveData<out UnitSpecificCurrentWeatherEntry>

    //suspend fun getWeeklyWeather(): LiveData<out List<UnitSpecificWeeklyForecastEntry>>

    //suspend fun getWeatherByDate(date: String): LiveData<out UnitSpecificWeeklyForecastEntry>
}