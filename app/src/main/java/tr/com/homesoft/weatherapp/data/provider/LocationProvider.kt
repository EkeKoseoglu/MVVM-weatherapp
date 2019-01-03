package tr.com.homesoft.weatherapp.data.provider

import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.remote.internal.LocationLiveData

interface LocationProvider {

    val locationLiveData: LocationLiveData

    suspend fun getPreferredLocation(): String
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
}