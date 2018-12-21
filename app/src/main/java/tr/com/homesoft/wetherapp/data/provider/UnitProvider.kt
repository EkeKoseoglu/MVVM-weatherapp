package tr.com.homesoft.wetherapp.data.provider

import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

interface UnitProvider {

    fun getUnitSystem(): UnitSystem
}