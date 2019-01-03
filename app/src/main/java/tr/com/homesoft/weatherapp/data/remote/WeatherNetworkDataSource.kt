package tr.com.homesoft.weatherapp.data.remote

import androidx.lifecycle.LiveData
import tr.com.homesoft.weatherapp.data.remote.response.WeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<WeatherResponse>

    suspend fun fetchCurrentWeather(location: String, lang: String)
}