package tr.com.homesoft.weatherapp.data.remote.internal

import android.Manifest
import android.app.Activity
import tr.com.homesoft.weatherapp.util.extensions.batchRequestPermissions
import tr.com.homesoft.weatherapp.util.extensions.isPermissionGranted
import java.lang.ref.WeakReference


internal class PermissionsRequester private constructor(private val activityWeakReference: WeakReference<Activity>) {

    fun hasPermissions(): Boolean {
        val activity = activityWeakReference.get()
        if (activity != null) {
            for (permission in PERMISSIONS) {
                if (!activity.isPermissionGranted(permission)) {
                    return false
                }
            }
            return true
        }
        return false
    }

    fun requestPermissions() {
        activityWeakReference.get()?.batchRequestPermissions(PERMISSIONS, 0)
    }

    companion object {
        private val PERMISSIONS =
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

        operator fun invoke(activity: Activity): PermissionsRequester {
            val activityWeakReference = WeakReference(activity)
            return PermissionsRequester(activityWeakReference)
        }
    }

}
