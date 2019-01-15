package tr.com.homesoft.weatherapp.ui.view.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.databinding.FutureDetailFragmentBinding
import tr.com.homesoft.weatherapp.ui.delegates.inflate

class FutureDetailFragment : Fragment() {

    private val viewModel  by inject<FutureDetailViewModel>()

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

        date = FutureDetailFragmentArgs.fromBundle(arguments!!).date

        with(binding) {
            vm = viewModel.apply { loading.set(true) }
            setLifecycleOwner(this@FutureDetailFragment.activity)
        }

        bindUI()
    }

    private fun bindUI() {

/*        with(viewModel) {

            val forecast = getDetailForecastByDate(date)

            forecast.observe(viewLifecycleOwner, Observer {

                loading.value = false
                setDetailForecast(it)
            }

            weatherLocation.observe(viewLifecycleOwner, Observer {
                (activity as AppCompatActivity).apply {

                    supportActionBar?.let { actionBar ->
                        with(actionBar) {
                            val local = LocalDate.parse(date)
                            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            subtitle = local.format(formatter)

                            title = it.name
                        }
                    }
                }
            })

        }*/

    }

}
