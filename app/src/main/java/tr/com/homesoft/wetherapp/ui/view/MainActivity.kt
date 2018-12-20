package tr.com.homesoft.wetherapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        setupBottomNav(navController)

    }

    private fun setupBottomNav(navController: NavController) {
        with(navController) {
            binding.bottomNav.setupWithNavController(this)
            setupActionBarWithNavController(this, null)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(null)
}
