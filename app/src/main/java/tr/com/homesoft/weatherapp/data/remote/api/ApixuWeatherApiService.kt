package tr.com.homesoft.weatherapp.data.remote.api


import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import tr.com.homesoft.weatherapp.data.remote.ConnectivityInterceptor
import tr.com.homesoft.weatherapp.data.remote.response.WeatherResponse


interface ApixuWeatherApiService {

    @Headers("Content-Type: application/json")
    @GET("forecast.json")
    fun getForecastsByCity(
        @Query("q") city: String,
        @Query("lang") language: String? = null,
        @Query("days") days: String? = "7"
    ): Deferred<WeatherResponse>

    companion object {

        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApixuWeatherApiService {

            okHttpClient.newBuilder().addInterceptor(connectivityInterceptor)

            return retrofit.create(ApixuWeatherApiService::class.java)
        }

    }

}