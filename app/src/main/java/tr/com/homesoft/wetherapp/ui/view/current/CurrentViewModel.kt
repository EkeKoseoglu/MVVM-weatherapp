package tr.com.homesoft.wetherapp.ui.view.current

import androidx.lifecycle.ViewModel
import tr.com.homesoft.wetherapp.data.repository.ForecastRepository

class CurrentViewModel(private val repository: ForecastRepository) : ViewModel() {

    //internal suspend fun getCurrentWeather() = repository.getCurrentWeather()

    //internal suspend fun getLoation() = repository.getLocation()

    internal val location = repository.location

    internal val currentWeather = repository.currentWeather

}
