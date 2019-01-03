package tr.com.homesoft.weatherapp.util.extensions

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import tr.com.homesoft.weatherapp.R
import tr.com.homesoft.weatherapp.glide.GlideApp

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