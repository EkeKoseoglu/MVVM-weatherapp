package tr.com.homesoft.wetherapp

import org.koin.dsl.module.module

val testModule = module {
    factory { TestDispatcher() as DispatchersProvider }
}