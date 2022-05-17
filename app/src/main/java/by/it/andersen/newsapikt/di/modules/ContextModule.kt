package by.it.andersen.newsapikt.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun bindContext(application: Application): Context {
        return application
    }

}
