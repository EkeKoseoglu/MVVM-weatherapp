package tr.com.homesoft.wetherapp.ui.view.future.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository
import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

class FutureDetailViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) : ViewModel() {

    //internal fun getForecastByDate(date: String) = repository.getWeatherByDate(date)

    //internal suspend fun getForecastByDate(date: String) = repository.getWeatherByDate(date)

    //internal suspend fun getLocation() = repository.getLocation()

    internal val location = repository.location

    val loading = MutableLiveData<Boolean>()

    private val unitSystem = MutableLiveData<UnitSystem>()
    val metric: LiveData<Boolean> = Transformations.map(unitSystem) { it == UnitSystem.METRIC }

    private val mDetailForecast = MutableLiveData<UnitSpecificWeeklyForecastEntry>()
    val detailForecast: LiveData<UnitSpecificWeeklyForecastEntry> get() = mDetailForecast

    internal fun getDetailForecastByDate(date: String): LiveData<UnitSpecificWeeklyForecastEntry> =
        Transformations.switchMap(metric) { repository.getDetailWeatherByDate(date, it) }

    init {
        unitSystem.value = unitProvider.getUnitSystem()
    }

    internal fun setDetailForecast(data: UnitSpecificWeeklyForecastEntry) {
        mDetailForecast.value = data
    }

}
