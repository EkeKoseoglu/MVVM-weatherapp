package tr.com.homesoft.wetherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.local.entity.Location

class LocationProviderImpl(context: Context) : LocationProvider {

    override fun hasLocationChanged(lastLocation: Location) =
        lastLocation.name != getPreferredLocation()


    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getPreferredLocation() =
        with(appContext) {
            val keyForLocation = getString(R.string.pref_custom_location_key)
            val defaultLocation = getString(R.string.pref_loc_default_value)
            preferences.getString(keyForLocation, defaultLocation)!!
        }


}