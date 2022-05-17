package by.it.andersen.newsapikt.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.Categories
import by.it.andersen.newsapikt.data.network.ApiInterface
import by.it.andersen.newsapikt.data.repository.NewsRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsCategoriesViewModel @Inject constructor(
    articleDao: ArticleDao,
    apiInterface: ApiInterface
) : ViewModel() {

    private val repository: NewsRepository
    val articles = MutableLiveData<List<Categories>>()

    val errorMessage = MutableLiveData<String?>()

    val processMessage = MutableLiveData<String?>()

    init {
        repository = NewsRepository(articleDao, apiInterface)
    }

    fun sendData() {
        val response = repository.getSourcesRest()
        response
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.sources }
            .subscribe(getData())
    }

    private fun getData(): SingleObserver<List<Categories>?> {
        return object : SingleObserver<List<Categories>?> {
            override fun onSubscribe(d: Disposable) {
                processMessage.postValue("stop")
            }

            override fun onSuccess(t: List<Categories>) {
                articles.value = t
            }

            override fun onError(e: Throwable) {
                errorMessage.postValue("error")
            }
        }
    }
}