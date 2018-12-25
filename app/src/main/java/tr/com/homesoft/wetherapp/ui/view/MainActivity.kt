package tr.com.homesoft.wetherapp.ui.view

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.remote.internal.PermissionsRequester
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val permissionsRequester: PermissionsRequester by lazy { PermissionsRequester(this) }

    private val fusedLocationProviderClient: FusedLocationProviderClient by inject()

    private lateinit var locationChannel: ReceiveChannel<Location>

    private val locationManager: BoundLocationManager by lazy { BoundLocationManager(fusedLocationProviderClient) }

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupBottomNav(navController)

        if (!permissionsRequester.hasPermissions()) {
            permissionsRequester.requestPermissions()
        }

    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        job = Job()
        val locationRequest = LocationRequest().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationChannel = locationManager.observeLocation(locationRequest)

        launch {
            locationChannel.consumeEach {

            }
        }
    }

    override fun onStop() {
        job.cancel()
        locationChannel.cancel()
        super.onStop()
    }

/*    private fun bindLocationManager() {
        LifecycleBoundLocationManager(this, fusedLocationProviderClient, locationCallback)
    }*/

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
