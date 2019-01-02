package tr.com.homesoft.wetherapp.di

import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.singleBy
import tr.com.homesoft.wetherapp.data.local.ForecastDatabase
import tr.com.homesoft.wetherapp.data.provider.LocationProvider
import tr.com.homesoft.wetherapp.data.provider.LocationProviderImpl
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.provider.UnitProviderImpl
import tr.com.homesoft.wetherapp.data.remote.ConnectivityInterceptor
import tr.com.homesoft.wetherapp.data.remote.ConnectivityInterceptorImpl
import tr.com.homesoft.wetherapp.data.remote.WeatherNetworkDataSource
import tr.com.homesoft.wetherapp.data.remote.WeatherNetworkDataSourceImpl
import tr.com.homesoft.wetherapp.data.remote.api.ApixuWeatherApiService
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository
import tr.com.homesoft.wetherapp.data.repository.ForecastRepositoryImpl
import tr.com.homesoft.wetherapp.ui.view.current.CurrentViewModel
import tr.com.homesoft.wetherapp.ui.view.future.detail.FutureDetailViewModel
import tr.com.homesoft.wetherapp.ui.view.future.list.FutureListViewModel


val appModule = module {

    single { ForecastDatabase(get()) }
    single { get<ForecastDatabase>().currentWeatherDao() }
    single { get<ForecastDatabase>().weeklyWeatherDao() }
    single { get<ForecastDatabase>().locationDao() }

    singleBy<ConnectivityInterceptor, ConnectivityInterceptorImpl>()

    single { ApixuWeatherApiService(get()) }

    factory { LocationServices.getFusedLocationProviderClient(androidApplication()) }

    singleBy<UnitProvider, UnitProviderImpl>()
    singleBy<LocationProvider, LocationProviderImpl>()
    singleBy<WeatherNetworkDataSource, WeatherNetworkDataSourceImpl>()
    singleBy<ForecastRepository, ForecastRepositoryImpl>()

    viewModel<CurrentViewModel>()

    viewModel<FutureListViewModel>()

    viewModel<FutureDetailViewModel>()

}