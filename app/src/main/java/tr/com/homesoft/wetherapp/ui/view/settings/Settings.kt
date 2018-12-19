package tr.com.homesoft.wetherapp.ui.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import tr.com.homesoft.wetherapp.R

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
