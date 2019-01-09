package tr.com.homesoft.weatherapp.ui.view

import android.content.Context
import androidx.lifecycle.ViewModel
import tr.com.homesoft.weatherapp.data.provider.GpsStatusListener

class MainViewModel(context: Context): ViewModel() {

    val gpsStatusLiveData = GpsStatusListener(context)
}