package tr.com.homesoft.weatherapp.util.extensions

import timber.log.Timber

inline fun <reified T> T.logd(message: () -> String) = Timber.tag(T::class.TAG()).d(message())


inline fun <reified T> T.loge(error: Throwable, message: () -> String) = Timber.tag(T::class.TAG()).e(error, message())


/**
 * Extension method to get the TAG name for all object
 */
fun <T : Any> T.TAG() = this::class.simpleName
