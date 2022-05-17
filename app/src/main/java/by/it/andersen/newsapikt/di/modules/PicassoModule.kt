package by.it.andersen.newsapikt.di.modules

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class PicassoModule {
    @Provides
    @Singleton
    fun providePicasso(
        application: Application?,
        okHttp3Downloader: OkHttp3Downloader?
    ): Picasso {
        return Picasso.Builder(application!!).downloader(okHttp3Downloader!!).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpDowmnloader(okHttpClient: OkHttpClient?): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }
}