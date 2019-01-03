package tr.com.homesoft.weatherapp.ui.view.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.databinding.CurrentFragmentBinding
import tr.com.homesoft.weatherapp.ui.delegates.inflate
import tr.com.homesoft.weatherapp.ui.state.UIState

class CurrentFragment : Fragment() {

    private val viewModel: CurrentViewModel by inject()

    private val binding: CurrentFragmentBinding by inflate(R.layout.current_fragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding) {
            setLifecycleOwner(this@CurrentFragment.activity)
            vm = viewModel
            viewModel.uiState.value = UIState.Loading
        }
        bindUI()
    }

    private fun bindUI() {

        with(viewModel) {

            uiState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    UIState.Loading -> {
                        loading.set(true)
                    }

                    UIState.HasData -> {
                        loading.set(false)
                    }

                    is Error -> {
                        loading.set(false)
                    }
                }
            })

            weatherLocation.observe(viewLifecycleOwner, Observer { location ->
                if (location == null) return@Observer
                updateLocation(location.name)
                updateDateToToday()
            })

            weather.observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                uiState.value = UIState.HasData
            })

        }
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar
            ?.subtitle = getString(R.string.today)
    }

}
