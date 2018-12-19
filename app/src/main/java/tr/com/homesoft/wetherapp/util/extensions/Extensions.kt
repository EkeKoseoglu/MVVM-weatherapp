package tr.com.homesoft.wetherapp.util.extensions

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.engine.DiskCacheStrategy
import tr.com.homesoft.wetherapp.R
import tr.com.homesoft.wetherapp.glide.GlideApp

inline fun <reified T> T.logd(message: () -> String) = Log.d(T::class.TAG(), message())

inline fun <reified T> T.loge(error: Throwable, message: () -> String) = Log.d(T::class.TAG(), message(), error)

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}


/**
 * Extension method to get the TAG name for all object
 */
fun <T : Any> T.TAG() = this::class.simpleName

fun ImageView.loadUrl(url: String) {
    GlideApp.with(context)
        .load("http:$url")
        .placeholder(R.drawable.placeholder64)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .dontTransform()
        .thumbnail(.1f)
        .into(this)
}

/**
 * Extension method to simplify view inflating and binding inside a [ViewGroup].
 *
 * e.g.
 * This:
 *<code>
 *     binding = bind(R.layout.widget_card)
 *</code>
 *
 * Will replace this:
 *<code>
 *     binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.widget_card, this, true)
 *</code>
 */
fun <T : ViewDataBinding> ViewGroup.bind(@LayoutRes layoutId: Int, shouldAttachToRootImmediately: Boolean = false): T =
    DataBindingUtil.inflate(this.getLayoutInflater(), layoutId, this, shouldAttachToRootImmediately)


/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getLayoutInflater() = context.getLayoutInflater()

/**
 * Extension method to provide quicker access to the [LayoutInflater] from [Context].
 */
fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

/**
 * Create an intent for [T] and apply a lambda on it
 */
inline fun <reified T : Any> Context.intent(body: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
    intent.body()
    return intent
}

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }

