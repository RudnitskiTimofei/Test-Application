package by.it.andersen.newsapikt.di.modules

import by.it.andersen.newsapikt.presentation.view.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeNewsCategoriesFragment(): NewsCategoriesFragment?

    @ContributesAndroidInjector
    abstract fun contributeNewsListFragment(): NewsListFragment?

    @ContributesAndroidInjector
    abstract fun contributeNewsDescriptionFragment(): NewsDescriptionFragment?
}