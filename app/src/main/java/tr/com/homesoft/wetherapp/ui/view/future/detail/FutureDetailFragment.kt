package tr.com.homesoft.wetherapp.ui.view.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.databinding.FutureDetailFragmentBinding
import tr.com.homesoft.wetherapp.ui.delegates.inflate

class FutureDetailFragment : Fragment() {

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

        date = FutureDetailFragmentArgs.fromBundle(arguments!!).date

        with(binding) {
            vm = viewModel.apply { loading.value = true }
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
