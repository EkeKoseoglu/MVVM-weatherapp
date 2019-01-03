package tr.com.homesoft.weatherapp.data.provider

import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation

interface LocationProvider {

    suspend fun getPreferredLocation(): String
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
}