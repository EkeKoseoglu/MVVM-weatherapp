package tr.com.homesoft.wetherapp.ui.view.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.databinding.CurrentFragmentBinding
import tr.com.homesoft.wetherapp.ui.delegates.inflate
import tr.com.homesoft.wetherapp.ui.state.Error
import tr.com.homesoft.wetherapp.ui.state.HasData
import tr.com.homesoft.wetherapp.ui.state.Loading

class CurrentFragment : Fragment() {

    private val viewModel: CurrentViewModel by inject()

    private val binding: CurrentFragmentBinding by inflate(R.layout.current_fragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding) {
            setLifecycleOwner(this@CurrentFragment.activity)
            vm = viewModel.apply {
                uiState.value = Loading
            }
        }
        bindUI()
    }

    private fun bindUI() {

        with(viewModel) {

            uiState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    Loading -> {
                        loading.value = true
                    }
                    HasData -> {
                        loading.value = false
                    }
                    Error -> {
                        loading.value = false
                    }
                }
            })


            currentWeatherForecast.observe(viewLifecycleOwner, Observer {
                uiState.value = HasData
            })


            location.observe(viewLifecycleOwner, Observer {

                if (null == it) return@Observer

                (activity as AppCompatActivity).apply {

                    supportActionBar?.let { actionBar ->
                        with(actionBar) {
                            setSubtitle(R.string.today)
                            title = it.name
                        }
                    }
                }
            })


        }
    }

}
