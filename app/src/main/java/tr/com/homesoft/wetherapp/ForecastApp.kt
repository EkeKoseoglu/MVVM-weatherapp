package tr.com.homesoft.wetherapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.startKoin
import tr.com.homesoft.wetherapp.di.appModule

class ForecastApp: Application() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            AndroidThreeTen.init(this@ForecastApp)
        }
        startKoin(this, listOf(appModule))
    }
}