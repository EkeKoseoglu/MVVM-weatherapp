package tr.com.homesoft.wetherapp.ui.view.future.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository

class FutureDetailViewModel(private val repository: ForecastRepository) : ViewModel() {


    lateinit var loading: LiveData<Boolean>

    internal fun getForecastByDate(date: String) = repository.getWeatherByDate(date)

    //internal suspend fun getForecastByDate(date: String) = repository.getWeatherByDate(date)

    //internal suspend fun getLocation() = repository.getLocation()

    internal val location = repository.location
}
