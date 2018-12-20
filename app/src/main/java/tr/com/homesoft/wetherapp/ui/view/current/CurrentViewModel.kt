package tr.com.homesoft.wetherapp.ui.view.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository
import tr.com.homesoft.wetherapp.ui.view.UnitSystem

class CurrentViewModel(repository: ForecastRepository, unitProvider: UnitProvider) : ViewModel() {

    //internal suspend fun getCurrentWeather() = repository.getCurrentWeather()

    //internal suspend fun getLoation() = repository.getLocation()

    internal val location = repository.location

    internal val currentWeather = repository.currentWeather

    private val unitSystem = MutableLiveData<UnitSystem>()
    val metric: LiveData<Boolean> = Transformations.map(unitSystem) {it == UnitSystem.METRIC}

    val currentWeatherForecast: LiveData<UnitSpecificCurrentWeatherEntry> =
        Transformations.switchMap(metric) {
            repository.getCurrentWeather(it)
        }

    val loading = MutableLiveData<Boolean>()

    init {
        unitSystem.value = unitProvider.getUnitSystem()
    }

}
