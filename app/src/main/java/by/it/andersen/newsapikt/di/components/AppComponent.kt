package by.it.andersen.newsapikt.di.components

import android.app.Application
import by.it.andersen.newsapikt.BaseApplication
import by.it.andersen.newsapikt.di.modules.*
import by.it.andersen.newsapikt.presentation.view.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, PicassoModule::class, DataBaseModule::class,
        NewsViewModelModule::class, ActivityModule::class, FragmentModule::class, AndroidSupportInjectionModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }

    fun inject(application: BaseApplication?)
    fun inject(activity: MainActivity)
    fun inject(fragment: NewsCategoriesFragment)
    fun inject(fragment: NewsListFragment)
    fun inject(fragment: NewsDescriptionFragment)
}
