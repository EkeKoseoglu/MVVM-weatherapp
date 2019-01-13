package tr.com.homesoft.weatherapp.data.remote.internal

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener

class LocationLiveData(context: Context) : LiveData<Location>() {

    private val locationClient by lazy {LocationServices.getFusedLocationProviderClient(context)}

    private val locationCallback = object : LocationCallback(), OnSuccessListener<Location> {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.locations?.firstOrNull()?.let { value = it }
        }

        override fun onSuccess(location: Location?) {
            location?.let { value = it }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        // Try to immediately find a location
        locationClient.lastLocation.addOnSuccessListener(locationCallback)

        // Request updates if there’s someone observing
        if (hasActiveObservers()) {
            val locationRequest = LocationRequest().apply {
                interval = 5000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onInactive() {
        locationClient.removeLocationUpdates(locationCallback)
    }

}
