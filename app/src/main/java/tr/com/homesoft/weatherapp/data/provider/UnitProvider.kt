package tr.com.homesoft.weatherapp.data.provider

import tr.com.homesoft.weatherapp.ui.unitsystem.UnitSystem

interface UnitProvider {

    fun getUnitSystem(): UnitSystem
}