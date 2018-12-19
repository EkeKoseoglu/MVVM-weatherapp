package tr.com.homesoft.wetherapp.data.provider

import tr.com.homesoft.wetherapp.ui.view.UnitSystem

interface UnitProvider {

    fun getUnitSystem(): UnitSystem
}