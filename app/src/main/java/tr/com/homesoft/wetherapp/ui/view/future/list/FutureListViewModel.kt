package tr.com.homesoft.wetherapp.ui.view.future.list

import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository

class FutureListViewModel(private val repository: ForecastRepository) : ViewModel() {

    internal suspend fun getWeeklyForecast() = repository.getWeeklyWeather()

    internal suspend fun getLocation() = repository.getLocation()
}
