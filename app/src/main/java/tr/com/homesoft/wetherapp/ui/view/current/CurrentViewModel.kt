package tr.com.homesoft.wetherapp.ui.view.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository
import tr.com.homesoft.wetherapp.ui.base.AbsentLiveData
import tr.com.homesoft.wetherapp.ui.state.Loading
import tr.com.homesoft.wetherapp.ui.state.UIState
import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

class CurrentViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) : ViewModel() {

    //internal suspend fun getCurrentWeather() = repository.getCurrentWeather()

    //internal suspend fun getLoation() = repository.getWeatherLocation()

    val uiState = MutableLiveData<UIState<*>>()

    internal val location = repository.weatherLocation

    //internal val currentWeather = repository.currentWeather

    private val unitSystem = MutableLiveData<UnitSystem>()
    val metric: LiveData<Boolean> = Transformations.map(unitSystem) {
        it == UnitSystem.METRIC
    }

    val currentWeatherForecast: LiveData<UnitSpecificCurrentWeatherEntry> =
        Transformations.switchMap(metric) {
            if (metric == null)
                return@switchMap AbsentLiveData<UnitSpecificCurrentWeatherEntry>()
            repository.getCurrentWeather(it)
        }

    val loading = MutableLiveData<Boolean>()

    init {
        unitSystem.value = unitProvider.getUnitSystem()

    }

}
