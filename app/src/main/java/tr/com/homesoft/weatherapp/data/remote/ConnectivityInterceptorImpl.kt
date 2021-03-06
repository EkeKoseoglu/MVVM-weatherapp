package tr.com.homesoft.weatherapp.data.remote

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import tr.com.homesoft.weatherapp.data.remote.internal.NoConnectionException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectionException()
        }
        return chain.proceed(chain.request())
    }

    private fun isConnected(): Boolean {
        val connManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}