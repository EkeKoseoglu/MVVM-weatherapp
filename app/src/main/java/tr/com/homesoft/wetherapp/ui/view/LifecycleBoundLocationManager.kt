package tr.com.homesoft.wetherapp.ui.view

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LifecycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
): LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest().apply {
        interval = 5000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}