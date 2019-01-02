package tr.com.homesoft.wetherapp.ui.view.future.detail

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import tr.com.homesoft.wetherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository
import tr.com.homesoft.wetherapp.ui.base.ScopedViewModel
import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

class FutureDetailViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) : ScopedViewModel() {

    val weatherLocation: LiveData<WeatherLocation>  = getLocation()

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

    val loading = MutableLiveData<Boolean>()

    private val unitSystem = MutableLiveData<UnitSystem>()
    val metric: LiveData<Boolean> = Transformations.map(unitSystem) { it == UnitSystem.METRIC }

    private val mDetailForecast = MutableLiveData<UnitSpecificWeeklyForecastEntry>()
    val detailForecast: LiveData<UnitSpecificWeeklyForecastEntry> get() = mDetailForecast

    internal fun getDetailForecastByDate(date: String): LiveData<UnitSpecificWeeklyForecastEntry> =
        Transformations.switchMap(metric) { getWeatherByDate(date, it) }

    init {
        unitSystem.value = unitProvider.getUnitSystem()
    }

    private fun getWeatherByDate(date: String, isMetric: Boolean): LiveData<UnitSpecificWeeklyForecastEntry> {
        val result = MediatorLiveData<UnitSpecificWeeklyForecastEntry>()
        launch {
            val data = repository.getDetailWeatherByDate(date, isMetric)
            result.addSource(data) {
                result.postValue(it)
                result.removeSource(data)
            }
        }
        return result

    }
    internal fun setDetailForecast(data: UnitSpecificWeeklyForecastEntry) {
        mDetailForecast.value = data
    }

}
