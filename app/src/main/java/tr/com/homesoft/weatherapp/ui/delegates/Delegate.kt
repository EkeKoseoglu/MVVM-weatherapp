package tr.com.homesoft.weatherapp.ui.delegates

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

inline  fun <reified T: ViewDataBinding> bind(activity: AppCompatActivity, @LayoutRes layoutRes: Int): Lazy<T> {
    return lazy { DataBindingUtil.setContentView<T>(activity, layoutRes) }
}