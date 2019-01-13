package tr.com.homesoft.weatherapp.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.threeten.bp.LocalDate
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.util.extensions.getParentActivity
import tr.com.homesoft.weatherapp.util.extensions.loadUrl

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("formatDate")
    fun formatDate(view: TextView, date: String) {
        //view.text = date.formatDate("dd.MM.yyyy")
    }

    @JvmStatic
    @BindingAdapter("dateFormat")
    fun dateFormat(view: TextView, date: LocalDate) {
        view.text = date.toString()
    }

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

}