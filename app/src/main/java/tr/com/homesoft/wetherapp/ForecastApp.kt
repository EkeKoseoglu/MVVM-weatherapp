package tr.com.homesoft.wetherapp

import android.app.Application
import org.koin.android.ext.android.startKoin
import tr.com.homesoft.wetherapp.di.appModule

class ForecastApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}