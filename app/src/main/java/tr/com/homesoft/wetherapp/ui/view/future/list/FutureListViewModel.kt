package tr.com.homesoft.wetherapp.ui.view.future.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository
import tr.com.homesoft.wetherapp.ui.base.AbsentLiveData
import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

class FutureListViewModel(repository: ForecastRepository, unitProvider: UnitProvider) : ViewModel() {

    //internal suspend fun getWeeklyForecast() = repository.getWeeklyWeather()

    //internal suspend fun getWeatherLocation() = repository.getWeatherLocation()

    internal val location = repository.weatherLocation

    //internal val weeklyWeather = repository.weeklyWeather

    val loading = MutableLiveData<Boolean>()

    private val unitSystem = MutableLiveData<UnitSystem>()
    val metric: LiveData<Boolean> = Transformations.map(unitSystem) { it == UnitSystem.METRIC }

    val weeklyWeatherForecast: LiveData<List<UnitSpecificWeeklyForecastEntry>> =
        Transformations.switchMap(metric) {
            repository.getWeeklyWeather(it)
        }

    init {
        unitSystem.value = unitProvider.getUnitSystem()
    }
}
