package tr.com.homesoft.wetherapp.data.provider

import tr.com.homesoft.wetherapp.data.local.entity.Location

interface LocationProvider {

    fun getPreferredLocation(): String
    fun hasLocationChanged(lastLocation: Location): Boolean
}