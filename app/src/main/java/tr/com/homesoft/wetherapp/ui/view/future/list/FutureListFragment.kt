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
import tr.com.homesoft.wetherapp.databinding.FutureListFragmentBinding
import tr.com.homesoft.wetherapp.ui.adapter.FutureForecastAdapter
import tr.com.homesoft.wetherapp.ui.delegates.inflate
import tr.com.homesoft.wetherapp.ui.state.UIState

class FutureListFragment : Fragment() {

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
            vm = viewModel
            viewModel.uiState.value = UIState.Loading
        }

        bindUI()
    }

    private fun bindUI() {
        with(viewModel) {

            uiState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    UIState.Loading -> loading.set(true)
                    UIState.HasData -> {
                        loading.set(false)
                    }
                    UIState.Error -> loading.set(false)
                }
            })

            metric.observe(viewLifecycleOwner, Observer {
                forecastAdapter.isMetric = it
            })

            futureWeather.observe(viewLifecycleOwner, Observer {
                forecastAdapter.forecastDataList = it
                uiState.value = UIState.HasData
            })

            weatherLocation.observe(viewLifecycleOwner, Observer { location ->
                if (location == null) return@Observer
                updateLocation(location.name)
                updateDateToNextWeek()
            })
        }
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToNextWeek() {
        (activity as? AppCompatActivity)?.supportActionBar
            ?.subtitle = getString(R.string.weekly)
    }

    private fun detailItemClicked(unitSpecificWeeklyForecastEntry: Any) {
        val date = (unitSpecificWeeklyForecastEntry as UnitSpecificWeeklyForecastEntry).date.toString()
        val direction =
            FutureListFragmentDirections.actionFutureListFragmentToFutureDetailFragment(date)
        findNavController().navigate(direction)
    }

}
