package tr.com.homesoft.weatherapp.ui.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.sendBlocking

class BoundLocationManager(private val fusedLocationProviderClient: FusedLocationProviderClient) {

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    internal fun observeLocation(locationRequest: LocationRequest): ReceiveChannel<Location> {
        val channel = Channel<Location>()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                channel.sendBlocking(result.lastLocation)
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, Looper.myLooper())

        channel.invokeOnClose {
            fusedLocationProviderClient.removeLocationUpdates(callback)
        }

        return channel
    }
}