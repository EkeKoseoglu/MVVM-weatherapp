package tr.com.homesoft.weatherapp.ui.view.future.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import tr.com.homesoft.weatherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.weatherapp.data.provider.UnitProvider
import tr.com.homesoft.weatherapp.data.repository.ForecastRepository
import tr.com.homesoft.weatherapp.ui.base.WeatherViewModel

class FutureListViewModel(private val repository: ForecastRepository, unitProvider: UnitProvider) :
    WeatherViewModel(unitProvider, repository) {

    val futureWeather: LiveData<List<UnitSpecificWeeklyForecastEntry>> =
        Transformations.switchMap(isMetric) { getFutureWeather(it) }

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

}
