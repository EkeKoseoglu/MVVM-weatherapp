package tr.com.homesoft.weatherapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.startKoin
import tr.com.homesoft.weatherapp.di.appModule

class ForecastApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            AndroidThreeTen.init(this@ForecastApp)
        }
        startKoin(this, listOf(appModule))
    }
}