package by.it.andersen.newsapikt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.Article
import by.it.andersen.newsapikt.data.network.ApiInterface
import by.it.andersen.newsapikt.data.repository.NewsRepository
import javax.inject.Inject

class NewsListViewModel @Inject constructor(articleDao: ArticleDao, apiInterface: ApiInterface) : ViewModel() {

    private var mMessage: String? = null
    private val repository: NewsRepository
    private var articles: LiveData<List<Article?>?>? = MutableLiveData<List<Article?>?>()

    fun getArticles(string: String?): LiveData<List<Article?>?>? {
        mMessage = string
        articles = mMessage?.let { repository.getAllNews(it) }
        return articles
    }

    val errorMessage: LiveData<String?>
        get() = repository.errorMessage

    val processMessage: LiveData<String?>
        get() = repository.processMessage

    init {
        repository = NewsRepository(articleDao, apiInterface)
    }
}