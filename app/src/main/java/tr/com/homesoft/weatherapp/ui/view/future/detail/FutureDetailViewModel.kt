package tr.com.homesoft.weatherapp.ui.view.future.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import tr.com.homesoft.weatherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.weatherapp.data.provider.UnitProvider
import tr.com.homesoft.weatherapp.data.repository.ForecastRepository
import tr.com.homesoft.weatherapp.ui.base.WeatherViewModel

class FutureDetailViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) :
    WeatherViewModel(unitProvider, repository) {

    private val mDetailForecast = MutableLiveData<UnitSpecificWeeklyForecastEntry>()
    val detailForecast: LiveData<UnitSpecificWeeklyForecastEntry> get() = mDetailForecast

    internal fun getDetailForecastByDate(dateEpoch: Long): LiveData<UnitSpecificWeeklyForecastEntry> =
        Transformations.switchMap(isMetric) { getWeatherByDate(dateEpoch, it) }


    private fun getWeatherByDate(dateEpoch: Long, isMetric: Boolean): LiveData<UnitSpecificWeeklyForecastEntry> {
        val result = MediatorLiveData<UnitSpecificWeeklyForecastEntry>()
        launch {
            val data = repository.getDetailWeatherByDate(dateEpoch, isMetric)
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
