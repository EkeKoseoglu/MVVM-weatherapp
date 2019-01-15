package tr.com.homesoft.weatherapp.ui.view.future.detail

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
import org.threeten.bp.format.FormatStyle
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.databinding.FutureDetailFragmentBinding
import tr.com.homesoft.weatherapp.ui.delegates.inflate

class FutureDetailFragment : Fragment() {

    private val viewModel by inject<FutureDetailViewModel>()

    private val binding: FutureDetailFragmentBinding by inflate(R.layout.future_detail_fragment)

    private var dateEpoch: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments == null) {
            return
        }

        dateEpoch = FutureDetailFragmentArgs.fromBundle(arguments!!).dateEpoch

        with(binding) {
            vm = viewModel.apply { loading.set(true) }
            setLifecycleOwner(this@FutureDetailFragment.activity)
        }

        bindUI()
    }

    private fun bindUI() {

        var date: LocalDate? = null

        with(viewModel) {

            val forecast = getDetailForecastByDate(dateEpoch)

            forecast.observe(viewLifecycleOwner, Observer {

                loading.set(false)
                setDetailForecast(it)
                date = it.date
            })

            weatherLocation.observe(viewLifecycleOwner, Observer { location ->
                if (location == null) return@Observer
                updateLocation(location.name)
                date?.let { updateDateToNextWeek(it) }

            })
        }

    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToNextWeek(date: LocalDate) {
        (activity as? AppCompatActivity)?.supportActionBar
            ?.subtitle = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
    }
}
