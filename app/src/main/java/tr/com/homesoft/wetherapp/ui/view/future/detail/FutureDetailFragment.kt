package tr.com.homesoft.wetherapp.ui.view.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.data.provider.UnitProvider
import tr.com.homesoft.wetherapp.databinding.FutureDetailFragmentBinding
import tr.com.homesoft.wetherapp.ui.base.ScopedFragment
import tr.com.homesoft.wetherapp.ui.delegates.inflate
import tr.com.homesoft.wetherapp.ui.view.UnitSystem

class FutureDetailFragment : ScopedFragment() {

    private val unitProvider: UnitProvider by inject()

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

        date = FutureDetailFragmentArgs.fromBundle(arguments).date

        with(binding) {
            viewModel.loading = MutableLiveData<Boolean>().apply { value = true }
            isLoading = true
            setLifecycleOwner(this@FutureDetailFragment.activity)
        }

        bindUI()
    }

    private fun bindUI() = launch {

        with(viewModel) {


            val forecast = getForecastByDate(date)

            forecast.observe(viewLifecycleOwner, Observer {

                //val isLoading = Transformations.map(forecast) { f -> f == null }
                //(loading as MutableLiveData).value = isLoading.value

                with(binding) {
                    isMetric = unitProvider.getUnitSystem() == UnitSystem.METRIC
                    isLoading = false
                    obj = it
                }
            })

            /*
            getLocation().observe(viewLifecycleOwner, Observer { location ->
                (activity as AppCompatActivity).apply {

                    supportActionBar?.let {
                        with(it) {
                            subtitle = date
                            title = location.name
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
