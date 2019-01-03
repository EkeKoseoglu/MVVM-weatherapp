package tr.com.homesoft.weatherapp.ui.view.current

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.weatherapp.data.provider.UnitProvider
import tr.com.homesoft.weatherapp.data.repository.ForecastRepository
import tr.com.homesoft.weatherapp.ui.base.ScopedViewModel
import tr.com.homesoft.weatherapp.ui.state.UIState
import tr.com.homesoft.weatherapp.ui.unitsystem.UnitSystem

class CurrentViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) : ScopedViewModel() {

    private val unitSystem = MutableLiveData<UnitSystem>()

    val isMetric: LiveData<Boolean> = Transformations.map(unitSystem) { it == UnitSystem.METRIC }

    val weather: LiveData<UnitSpecificCurrentWeatherEntry> = Transformations.switchMap(isMetric) {
        getCurrentWeather(it)
    }

    private fun getCurrentWeather(metric: Boolean): LiveData<UnitSpecificCurrentWeatherEntry> {
        val current = MediatorLiveData<UnitSpecificCurrentWeatherEntry>()
        launch {
            val result = repository.getCurrentWeather(metric)
            current.addSource(result) {
                current.postValue(it)
                current.removeSource(result)
            }
        }
        return current
    }

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
