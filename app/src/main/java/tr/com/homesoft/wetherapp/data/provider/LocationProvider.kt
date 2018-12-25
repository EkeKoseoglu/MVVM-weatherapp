package tr.com.homesoft.wetherapp.data.provider

import tr.com.homesoft.wetherapp.data.local.entity.WeatherLocation

interface LocationProvider {

    suspend fun getPreferredLocation(): String
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
}