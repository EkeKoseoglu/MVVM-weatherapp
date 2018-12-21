package tr.com.homesoft.wetherapp.ui.view.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.databinding.FutureListFragmentBinding
import tr.com.homesoft.wetherapp.ui.adapter.FutureForecastAdapter
import tr.com.homesoft.wetherapp.ui.delegates.inflate
import tr.com.homesoft.wetherapp.ui.unitsystem.UnitSystem

class FutureListFragment : Fragment() {

    private val unitProvider: UnitProvider by inject()

    private val viewModel: FutureListViewModel by inject()

    private val binding: FutureListFragmentBinding by inflate(R.layout.future_list_fragment)

    private val forecastAdapter: FutureForecastAdapter by lazy {
        FutureForecastAdapter { unitSpecificWeeklyForecastEntry: Any ->
            detailItemClicked(
                unitSpecificWeeklyForecastEntry
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding) {
            with(forecastRv) {
                adapter = forecastAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }

            setLifecycleOwner(this@FutureListFragment.activity)
            vm = viewModel.apply { loading.value = true }
            //loading = true
        }

        bindUI()
    }

    private fun bindUI() {

        with(viewModel) {

            weeklyWeatherForecast.observe(viewLifecycleOwner, Observer {
                if (null == it) return@Observer

                loading.value = false
                with(forecastAdapter) {
                    isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                    forecastDataList = it
                }
            })

            /*
            weeklyWeather.observe(viewLifecycleOwner, Observer {
                if (null == it) return@Observer

                loading.value = false
                //binding.loading = false

                with(forecastAdapter) {
                    isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                    forecastDataList = it
                }
            })
            */
            /*
            getWeeklyForecast().observe(viewLifecycleOwner, Observer {

                if (null == it) return@Observer

                binding.loading = false

                with(forecastAdapter) {
                    isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                    forecastDataList = it
                }
            })
            */
/*
            getWeatherLocation().observe(viewLifecycleOwner, Observer { weatherLocation ->
                (activity as AppCompatActivity).apply {

                    supportActionBar?.let {
                        with(it) {
                            setSubtitle(R.string.weekly)
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
                            setSubtitle(R.string.weekly)
                            title = it.name
                        }
                    }
                }
            })
        }
    }

    private fun detailItemClicked(unitSpecificWeeklyForecastEntry: Any) {
        val date = (unitSpecificWeeklyForecastEntry as UnitSpecificWeeklyForecastEntry).date
        val direction =
            FutureListFragmentDirections.actionFutureListFragmentToFutureDetailFragment(date)
        findNavController().navigate(direction)
    }

}
