package tr.com.homesoft.weatherapp.ui.view.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import tr.com.homesoft.weatherapp.R

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).apply {

            supportActionBar?.let {
                with(it) {
                    setTitle(R.string.settings)
                    subtitle = ""
                }
            }
        }
    }
}
