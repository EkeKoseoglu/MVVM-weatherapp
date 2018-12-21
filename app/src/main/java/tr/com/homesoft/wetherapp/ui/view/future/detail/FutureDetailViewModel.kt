package tr.com.homesoft.wetherapp.ui.view.future.detail

import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository

class FutureDetailViewModel(private val repository: ForecastRepository) : ViewModel() {

    internal fun getForecastByDate(date: String) = repository.getWeatherByDate(date)

    //internal suspend fun getForecastByDate(date: String) = repository.getWeatherByDate(date)

    //internal suspend fun getLocation() = repository.getLocation()

    internal val location = repository.location
}
