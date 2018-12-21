package tr.com.homesoft.wetherapp.data.provider

import tr.com.homesoft.wetherapp.data.local.entity.WeatherLocation

interface LocationProvider {

    fun getPreferredLocation(): String
    fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
}