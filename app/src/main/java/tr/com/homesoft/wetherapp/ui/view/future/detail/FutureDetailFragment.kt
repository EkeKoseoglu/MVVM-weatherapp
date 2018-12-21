package tr.com.homesoft.wetherapp.ui.view.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.databinding.FutureDetailFragmentBinding
import tr.com.homesoft.wetherapp.ui.delegates.inflate

class FutureDetailFragment : Fragment() {

    //private val unitProvider: UnitProvider by inject()

    private val viewModel: FutureDetailViewModel by inject()

    private val binding: FutureDetailFragmentBinding by inflate(R.layout.future_detail_fragment)

    private lateinit var date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        date = arguments?.let { FutureDetailFragmentArgs.fromBundle(it)  }?.let { date }!!
        //date = FutureDetailFragmentArgs.fromBundle(arguments).date

        with(binding) {
            vm = viewModel.apply { loading.value = true }
            //isLoading = true
            setLifecycleOwner(this@FutureDetailFragment.activity)
        }

        bindUI()
    }

    private fun bindUI() {

        with(viewModel) {

            val forecast = getDetailForecastByDate(date)

            forecast.observe(viewLifecycleOwner, Observer {

                loading.value = false
                setDetailForecast(it)
                /*
                with(binding) {
                    //isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                    //isLoading = false
                    //obj = it
                }
                */
            })

            /*
            getWeatherLocation().observe(viewLifecycleOwner, Observer { weatherLocation ->
                (activity as AppCompatActivity).apply {

                    supportActionBar?.let {
                        with(it) {
                            subtitle = date
                            title = weatherLocation.name
                        }
                    }
                }
            })
            */
            location.observe(viewLifecycleOwner, Observer {
                (activity as AppCompatActivity).apply {

                    supportActionBar?.let { actionBar ->
                        with(actionBar) {
                            subtitle = date

                            title = it.name
                        }
                    }
                }
            })

        }

    }

}
