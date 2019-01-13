package tr.com.homesoft.weatherapp.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.provider.UnitProvider
import tr.com.homesoft.weatherapp.data.repository.ForecastRepository
import tr.com.homesoft.weatherapp.ui.state.UIState
import tr.com.homesoft.weatherapp.ui.unitsystem.UnitSystem

abstract class WeatherViewModel(unitProvider: UnitProvider, private val repository: ForecastRepository): ScopedViewModel() {

    private val unitSystem = MutableLiveData<UnitSystem>()

    val isMetric: LiveData<Boolean> = Transformations.map(unitSystem) { it == UnitSystem.METRIC }

    val weatherLocation: LiveData<WeatherLocation> = getLocation()

    private fun getLocation(): LiveData<WeatherLocation> {
        val location = MediatorLiveData<WeatherLocation>()
        launch {
            val result = repository.getWeatherLocation()
            location.addSource(result) {
                location.postValue(it)
                location.removeSource(result)
            }
        }
        return location
    }

    internal val uiState = MutableLiveData<UIState>()

    val loading = ObservableBoolean()

    init {
        unitSystem.value = unitProvider.getUnitSystem()
    }
}