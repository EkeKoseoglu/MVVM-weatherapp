package tr.com.homesoft.weatherapp.data.provider

import android.content.Context
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.remote.internal.LocationLiveData
import tr.com.homesoft.weatherapp.data.remote.internal.LocationPermissionNotGrantedException
import tr.com.homesoft.weatherapp.util.extensions.logd


class LocationProviderImpl(
    context: Context
) : PreferenceProvider(context), LocationProvider {

    override val locationLiveData: LocationLiveData by lazy { LocationLiveData(context) }

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {

        val deviceLastLocation = try {

            hasDeviceLocationChanged(lastWeatherLocation)

        } catch (e: LocationPermissionNotGrantedException) {
            false
        }

        return deviceLastLocation || hasCustomLocationChanged(lastWeatherLocation)
    }


    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation) = if (isUsingDeviceLocation())
        false
    else
        getCustomLocationName().toLowerCase() != lastWeatherLocation.name.toLowerCase()


    private fun getCustomLocationName() =
        with(appContext) {
            preferences.getString(
                getString(R.string.pref_custom_location_key),
                getString(R.string.pref_loc_default_value)
            )!!
        }

    private fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = locationLiveData.value ?: return false

        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }


    private fun isUsingDeviceLocation() = with(appContext.getString(R.string.pref_use_device_location_key)) {
        preferences.getBoolean(this, true)
    }

    override suspend fun getPreferredLocation(): String {
        if (isUsingDeviceLocation()) {
            try {
                logd { "TAG locationLiveData value : ${locationLiveData.value?.latitude}, ${locationLiveData.value?.longitude}" }
                //val deviceLocation = getLastDeviceLocation() ?: return getCustomLocationName()
                val deviceLocation = locationLiveData.value ?: return getCustomLocationName()
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return getCustomLocationName()
            }
        } else {
            return getCustomLocationName()
        }
    }

/*    private suspend fun <T> Task<T>.completeListener() = suspendCoroutine<T> { cont ->
        this.addOnCompleteListener {
            @Suppress("UNCHECKED_CAST")
            if (this.isSuccessful)
                cont.resume(this.result as T)
            else
                cont.resumeWithException(this.exception!!)
        }
    }*/

}

