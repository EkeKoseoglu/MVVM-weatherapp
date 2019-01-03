package tr.com.homesoft.weatherapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tr.com.homesoft.weatherapp.data.remote.api.ApixuWeatherApiService
import tr.com.homesoft.weatherapp.data.remote.internal.NoConnectionException
import tr.com.homesoft.weatherapp.data.remote.response.WeatherResponse
import tr.com.homesoft.weatherapp.util.extensions.TAG

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) :
    WeatherNetworkDataSource {

    private val mDownloadedCurrentWeather = MutableLiveData<WeatherResponse>()

    override val downloadedCurrentWeather: LiveData<WeatherResponse>
        get() = mDownloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, lang: String) {
        try {
            val response =
                apixuWeatherApiService.getForecastsByCity(location, lang).await()

            mDownloadedCurrentWeather.postValue(response)

        } catch (e: NoConnectionException) {
            Log.e(TAG(), "NO internet connection")
        }
    }
}