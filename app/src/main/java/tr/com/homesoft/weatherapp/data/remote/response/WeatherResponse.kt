package tr.com.homesoft.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry
import tr.com.homesoft.weatherapp.data.local.entity.Forecast
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation

data class WeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val forecast: Forecast,
    @SerializedName("location")
    val weatherLocation: WeatherLocation
)