package tr.com.homesoft.weatherapp.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import tr.com.homesoft.weatherapp.data.remote.api.ApixuWeatherApiService
import tr.com.homesoft.weatherapp.data.remote.internal.NoConnectionException
import tr.com.homesoft.weatherapp.data.remote.response.WeatherResponse
import tr.com.homesoft.weatherapp.util.extensions.TAG

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) :
    WeatherNetworkDataSource {

    private val mDownloadedWeather = MutableLiveData<WeatherResponse>()

    override val downloadedWeather: LiveData<WeatherResponse>
        get() = mDownloadedWeather

    override suspend fun fetchWeather(location: String, lang: String) {
        try {
            val response =
                apixuWeatherApiService.getForecastsByCity(location, lang).await()

            mDownloadedWeather.postValue(response)

        } catch (e: NoConnectionException) {
            Timber.tag(TAG()).e("NO internet connection")
        }
    }
}