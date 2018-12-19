package tr.com.homesoft.wetherapp.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.util.extensions.getParentActivity
import tr.com.homesoft.wetherapp.util.extensions.loadUrl

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("mutableVisibility")
    fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
        val parentActivity: AppCompatActivity? = view.getParentActivity()
        if (parentActivity != null && visibility != null) {
            visibility.observe(parentActivity, Observer { value -> view.visibility = value ?: View.VISIBLE })
        }
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun View.bindVisibility(shown: Boolean) {
        visibility = if (shown) View.VISIBLE else View.GONE
    }


    @JvmStatic
    @BindingAdapter("minTemp", "maxTemp", "metric")
    fun setFormatMixMaxTemp(view: TextView, minTemp: Double, maxTemp: Double, isMetric: Boolean) {

        val context = view.context

        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.format_min_max_temp_metric)
                else getString(R.string.format_min_max_temp_imperial)
            }.run { String.format(this, minTemp, maxTemp) }
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun loadImage(image: ImageView, imageUrl: String?) {

        imageUrl?.let { image.loadUrl(it) }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun loadWindDirection(view: ImageView, windDirection: Int) = with(view) {

        windDirection.run {
            when {
                this >= 22.5 && this < 67.5 -> R.drawable.wind_direction_ne
                this >= 67.5 && this < 112.5 -> R.drawable.wind_direction_e
                this >= 112.5 && this < 157.5 -> R.drawable.wind_direction_se
                this >= 157.5 && this < 202.5 -> R.drawable.wind_direction_s
                this >= 202.5 && this < 247.5 -> R.drawable.wind_direction_sw
                this >= 247.5 && this < 292.5 -> R.drawable.wind_direction_w
                this >= 292.5 && this < 337.5 -> R.drawable.wind_direction_nw
                else -> R.drawable.wind_direction_n
            }
        }.let {
            setImageResource(it)
        }
    }

    @JvmStatic
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @BindingAdapter("formatTemp", "metric")
    fun setFormatTemp(view: TextView, temperature: Double, isMetric: Boolean) {

        val context = view.context

        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.format_temp_metric)
                else getString(R.string.format_temp_imperial)
            }.run { String.format(this, temperature) }
        }
    }

    @JvmStatic
    @BindingAdapter("formatWind", "metric")
    fun formatWind(view: TextView, windSpeed: Double, isMetric: Boolean) {

        val context = view.context

        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.format_wind_speed_metric)
                else getString(R.string.format_wind_speed_imperial)
            }.run { String.format(this, windSpeed) }
        }
    }

    @JvmStatic
    @BindingAdapter("formatPrecipitaion", "metric")
    fun formatPrecipitation(view: TextView, precip: Double, isMetric: Boolean) {

        val context = view.context

        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.format_precip_metric)
                else getString(R.string.format_precip_imperial)
            }.run { String.format(this, precip) }
        }
    }

    @JvmStatic
    @BindingAdapter("formatVisibilityDistance", "metric")
    fun formatVisibilityDistance(view: TextView, visibilityDistance: Double, isMetric: Boolean) {

        val context = view.context


        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.format_visibility_metric)
                else getString(R.string.format_visibility_imperial)
            }.run { String.format(this, visibilityDistance) }
        }
    }

    @JvmStatic
    @BindingAdapter("formatPressure", "metric")
    fun formatPressure(view: TextView, pressure: Double, isMetric: Boolean) {
        val context = view.context


        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.format_pressure_metric)
                else getString(R.string.format_pressure_imperial)
            }.run { String.format(this, pressure) }
        }
    }

    @JvmStatic
    @BindingAdapter("formatFeelsLikeTemperature", "metric")
    fun formatFeelsLikeTemperature(view: TextView, feelsLikeTemperature: Double, isMetric: Boolean) {
        val context = view.context


        with(context) {

            view.text = with(resources) {
                if (isMetric) getString(R.string.feels_like_temp_metric)
                else getString(R.string.feels_like_temp_imperial)
            }.run { String.format(this, feelsLikeTemperature) }
        }
    }

}