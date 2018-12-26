package tr.com.homesoft.wetherapp.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.wetherapp.data.remote.internal.LocationPermissionNotGrantedException
import tr.com.homesoft.wetherapp.data.remote.internal.asDeferred
import tr.com.homesoft.wetherapp.util.extensions.isPermissionGranted
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation) =
        hasDeviceLocationChanged(lastWeatherLocation) || hasCustomLocationChanged(lastWeatherLocation)


    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation) =
        getCustomLocationName().toLowerCase() != lastWeatherLocation.name.toLowerCase()


    private fun getCustomLocationName() =
        with(appContext) {
            preferences.getString(
                getString(R.string.pref_custom_location_key),
                getString(R.string.pref_loc_default_value)
            )!!
        }


    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation() ?: return false

        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastDeviceLocation(): Location? = if (hasLocationPermission())
        suspendCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    continuation.resume(task.result)
                else
                    continuation.resumeWithException(task.exception!!)
            }
        }
    else
        throw LocationPermissionNotGrantedException()

    private fun hasLocationPermission() =
        appContext.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun isUsingDeviceLocation() = with(appContext.getString(R.string.pref_use_device_location_key)) {
        preferences.getBoolean(this, true)
    }

    override suspend fun getPreferredLocation(): String {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation() ?: return getCustomLocationName()
                return "${deviceLocation.latitude}, ${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return getCustomLocationName()
            }
        } else {
            return getCustomLocationName()
        }
    }


}