package by.it.andersen.newsapikt.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.it.andersen.newsapikt.di.util.ViewModelKey
import by.it.andersen.newsapikt.presentation.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    abstract fun bindNewsListViewModel(newsViewModel: NewsListViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(NewsDescriptionViewModel::class)
    abstract fun bindNewsDescriptionViewModel(newsViewModel: NewsDescriptionViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(NewsCategoriesViewModel::class)
    abstract fun bindNewsCategoriesViewModel(newsViewModel: NewsCategoriesViewModel?): ViewModel?


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory?): ViewModelProvider.Factory?
}
