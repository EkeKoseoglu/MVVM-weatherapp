package tr.com.homesoft.weatherapp.data.remote.internal

import kotlinx.coroutines.*

fun <T> lazyDeferred (block: suspend CoroutineScope.() -> T) : Lazy<Deferred<T>> {
    return lazy {
        CoroutineScope(Dispatchers.IO).async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}