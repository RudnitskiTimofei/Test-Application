package by.it.andersen.newsapikt.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.Article
import by.it.andersen.newsapikt.data.network.ApiInterface
import by.it.andersen.newsapikt.data.repository.NewsRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsDescriptionViewModel @Inject constructor(articleDao: ArticleDao, apiInterface: ApiInterface) : ViewModel() {

    private val repository: NewsRepository

    val news = MutableLiveData<Article>()

    val errorMessage = MutableLiveData<String?>()

    val processMessage = MutableLiveData<String?>()

    fun sendData(title: String) {
        val response = repository.getNews(title)
        response
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.articles?.get(0) }
            .subscribe(getData())
    }

    private fun getData(): SingleObserver<Article?> {
        return object : SingleObserver<Article?> {
            override fun onSubscribe(d: Disposable) {
                processMessage.postValue("stop")
            }

            override fun onSuccess(t: Article) {
                news.postValue(t)
            }

            override fun onError(e: Throwable) {
                errorMessage.postValue("error")
            }

        }
    }

    init {
        repository = NewsRepository(articleDao, apiInterface)
    }
}