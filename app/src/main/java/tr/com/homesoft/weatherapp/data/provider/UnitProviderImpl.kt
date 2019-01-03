package tr.com.homesoft.weatherapp.data.provider

import android.content.Context
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.ui.unitsystem.UnitSystem

class UnitProviderImpl(context: Context): PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val keyForUnits = appContext.getString(R.string.pref_unit_system_key)
        val preferredUnits = preferences.getString(keyForUnits, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(preferredUnits!!)
    }
}