package tr.com.homesoft.weatherapp

import org.koin.dsl.module.module

val testModule = module {
    factory { TestDispatcher() as DispatchersProvider }
}