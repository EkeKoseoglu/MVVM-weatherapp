package tr.com.homesoft.weatherapp.ui.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.data.remote.internal.PermissionsRequester
import tr.com.homesoft.weatherapp.util.extensions.getViewModel
import tr.com.homesoft.weatherapp.util.gps.GpsStatus


class MainActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    private val permissionsRequester: PermissionsRequester by lazy { PermissionsRequester(this) }

    private val viewModel: MainViewModel by lazy { getViewModel { MainViewModel(this) } }

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (!permissionsRequester.hasPermissions()) {
            permissionsRequester.requestPermissions()
        }
        setupBottomNav(navController)
        subscribeToGpsListener()
    }

    private fun subscribeToGpsListener() =
        viewModel.gpsStatusLiveData.observe(this, Observer<GpsStatus> { status ->
            status?.let {
                updateGpsCheckUI(status)
            }
        })

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

    private fun updateGpsCheckUI(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                handleGpsAlertDialog(GpsStatus.Enabled())
            }

            is GpsStatus.Disabled -> {
                handleGpsAlertDialog(GpsStatus.Disabled())
            }
        }
    }

    /**
     *  Using current value of [GpsStatusListener] livedata as default
     */
    private fun handleGpsAlertDialog(status: GpsStatus = viewModel.gpsStatusLiveData.value as GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> hideGpsNotEnabledDialog()
            is GpsStatus.Disabled -> showGpsNotEnabledDialog()
        }
    }

    private fun showGpsNotEnabledDialog() {
        if (alertDialog?.isShowing == true) {
            return // null or already being shown
        }

        alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.gps_required_title)
            .setMessage(R.string.gps_required_body)
            .setPositiveButton(R.string.action_settings) { _, _ ->
                // Open app's settings.
                val intent = Intent().apply {
                    action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                }
                startActivity(intent)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun hideGpsNotEnabledDialog() {
        if (alertDialog?.isShowing == true) alertDialog?.dismiss()
    }

}
