package tr.com.homesoft.weatherapp.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import tr.com.homesoft.weatherapp.data.remote.api.okHttpClient
import java.io.InputStream

@GlideModule
@Excludes(
    OkHttpLibraryGlideModule::class
) // initialize OkHttp manually
class ForecastAppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

        super.registerComponents(context, glide, registry)

        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}