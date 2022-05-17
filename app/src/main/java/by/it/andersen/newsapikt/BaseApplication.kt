package by.it.andersen.newsapikt

import android.app.Application
import by.it.andersen.newsapikt.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class BaseApplication : Application(),
    HasAndroidInjector {


    @set:Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>? = null
    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector!!
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this)?.build()?.inject(this)
    }
}