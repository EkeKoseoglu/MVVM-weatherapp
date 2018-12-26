package tr.com.homesoft.wetherapp.data.repository

import androidx.lifecycle.LiveData
import tr.com.homesoft.wetherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry

interface ForecastRepository {

    fun getCurrentWeather(isMetric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry>

    fun getWeeklyWeather(isMetric: Boolean): LiveData<List<UnitSpecificWeeklyForecastEntry>>

    fun getDetailWeatherByDate(date: String, isMetric: Boolean): LiveData<UnitSpecificWeeklyForecastEntry>

    val weatherLocation: LiveData<WeatherLocation>

}