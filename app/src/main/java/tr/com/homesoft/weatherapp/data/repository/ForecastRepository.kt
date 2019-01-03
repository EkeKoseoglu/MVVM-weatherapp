package tr.com.homesoft.weatherapp.data.repository

import androidx.lifecycle.LiveData
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.weatherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(isMetric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getWeeklyWeather(isMetric: Boolean): LiveData<out List<UnitSpecificWeeklyForecastEntry>>

    suspend fun getDetailWeatherByDate(date: String, isMetric: Boolean): LiveData<out UnitSpecificWeeklyForecastEntry>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

}