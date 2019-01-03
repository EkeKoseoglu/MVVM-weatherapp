package tr.com.homesoft.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {
    internal val appContext = context.applicationContext

    internal val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}