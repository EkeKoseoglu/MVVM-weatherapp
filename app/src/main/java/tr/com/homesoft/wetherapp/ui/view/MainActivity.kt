package tr.com.homesoft.wetherapp.ui.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.remote.internal.PermissionsRequester


class MainActivity : AppCompatActivity() {
    private val permissionsRequester: PermissionsRequester by lazy { PermissionsRequester(this) }

    private val fusedLocationProviderClient: FusedLocationProviderClient by inject()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupBottomNav(navController)

        if (!permissionsRequester.hasPermissions()) {
            permissionsRequester.requestPermissions()
        } else {
            bindLocationManager()
        }

    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(this, fusedLocationProviderClient, locationCallback)
    }

    private fun setupBottomNav(navController: NavController) {
        with(navController) {
            bottom_nav.setupWithNavController(this)
            setupActionBarWithNavController(this, null)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(null)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(main_layout, R.string.no_permissions, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
