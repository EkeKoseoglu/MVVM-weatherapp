package tr.com.homesoft.weatherapp.data.remote

import androidx.lifecycle.LiveData
import tr.com.homesoft.weatherapp.data.remote.response.WeatherResponse

interface WeatherNetworkDataSource {

    val downloadedWeather: LiveData<WeatherResponse>

    suspend fun fetchWeather(location: String, lang: String)
}