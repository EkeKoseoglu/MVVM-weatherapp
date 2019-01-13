package tr.com.homesoft.weatherapp.ui.view.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import tr.com.homesoft.weatherapp.data.local.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import tr.com.homesoft.weatherapp.data.provider.UnitProvider
import tr.com.homesoft.weatherapp.data.repository.ForecastRepository
import tr.com.homesoft.weatherapp.ui.base.WeatherViewModel

class CurrentViewModel(unitProvider: UnitProvider, private val repository: ForecastRepository) :
    WeatherViewModel(unitProvider, repository) {

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


}
