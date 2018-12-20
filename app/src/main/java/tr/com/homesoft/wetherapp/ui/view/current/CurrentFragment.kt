package tr.com.homesoft.wetherapp.ui.view.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.databinding.CurrentFragmentBinding
import tr.com.homesoft.wetherapp.ui.base.ScopedFragment
import tr.com.homesoft.wetherapp.ui.delegates.inflate
import tr.com.homesoft.wetherapp.ui.view.UnitSystem
import tr.com.homesoft.wetherapp.util.extensions.logd

class CurrentFragment : Fragment() {

    private val unitProvider: UnitProvider by inject()

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
            vm = viewModel.also { it.loading.value = true }
            //loading = true
        }

        bindUI()
    }

    private fun bindUI()  {

        with(viewModel) {

            currentWeatherForecast.observe(viewLifecycleOwner, Observer {
                loading.value = false
            })


            /*
                        getCurrentWeather().observe(viewLifecycleOwner, Observer {

                            if (null == it) return@Observer

                            with(binding) {
                                loading = false
                                isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                                currentWeather = it
                            }

                        })
            */
            /*
            currentWeather.observe(viewLifecycleOwner, Observer {
                if (null == it) return@Observer

                with(binding) {
                    loading = false
                    isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                    currentWeather = it
                }
            })
*/

            /*
            getLoation().observe(viewLifecycleOwner, Observer { location ->
                if (null == location) return@Observer
                (activity as AppCompatActivity).apply {

                    supportActionBar?.let {
                        with(it) {
                            setSubtitle(R.string.today)
                            title = location.name
                        }
                    }
                }

            })
            */

            location.observe(viewLifecycleOwner, Observer {

                if (null ==it) return@Observer

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
