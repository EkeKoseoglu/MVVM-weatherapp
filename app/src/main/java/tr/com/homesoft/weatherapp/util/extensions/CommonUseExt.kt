package tr.com.homesoft.weatherapp.util.extensions

import android.util.Log

inline fun <reified T> T.logd(message: () -> String) = Log.d(T::class.TAG(), message())

inline fun <reified T> T.loge(error: Throwable, message: () -> String) = Log.d(T::class.TAG(), message(), error)


/**
 * Extension method to get the TAG name for all object
 */
fun <T : Any> T.TAG() = this::class.simpleName
