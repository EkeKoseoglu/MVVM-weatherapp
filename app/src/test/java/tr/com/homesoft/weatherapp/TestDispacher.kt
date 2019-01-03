package tr.com.homesoft.weatherapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TestDispatcher : DispatchersProvider {

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}
