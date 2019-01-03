package tr.com.homesoft.weatherapp.data.remote.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "9f333155ad014853b44141442182511"

private const val KEY = "key"

private const val BASE_URL = "http://api.apixu.com/v1/"

private val requestInterceptor: Interceptor
    get() = Interceptor { chain ->

        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter(KEY, API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .addHeader("Connection", "close")
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()

internal val retrofit: Retrofit
    get() = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
