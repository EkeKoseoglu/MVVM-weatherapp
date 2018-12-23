package tr.com.homesoft.wetherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

class UnitProviderImpl(context: Context): PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val keyForUnits = appContext.getString(R.string.pref_unit_system_key)
        val preferredUnits = preferences.getString(keyForUnits, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(preferredUnits!!)
    }
}