package tr.com.homesoft.weatherapp.ui.view.future.list

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.weatherapp.data.provider.UnitProvider
import tr.com.homesoft.weatherapp.data.repository.ForecastRepository
import tr.com.homesoft.weatherapp.ui.base.ScopedViewModel
import tr.com.homesoft.weatherapp.ui.state.UIState
import tr.com.homesoft.weatherapp.ui.unitsystem.UnitSystem

class FutureListViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) : ScopedViewModel() {
    private val unitSystem = MutableLiveData<UnitSystem>()

    val metric: LiveData<Boolean> = Transformations.map(unitSystem) { it == UnitSystem.METRIC }

    val futureWeather: LiveData<List<UnitSpecificWeeklyForecastEntry>> =
        Transformations.switchMap(metric) { getFutureWeather(it) }

    private fun getFutureWeather(isMetric: Boolean): LiveData<List<UnitSpecificWeeklyForecastEntry>> {
        val future = MediatorLiveData<List<UnitSpecificWeeklyForecastEntry>>()
        launch {
            val result = repository.getWeeklyWeather(isMetric)
            future.addSource(result) {
                future.postValue(it)
                future.removeSource(result)
            }
        }
        return future
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

    val loading = ObservableBoolean()

    val uiState = MutableLiveData<UIState>()

    init {
        unitSystem.value = unitProvider.getUnitSystem()
    }
}
