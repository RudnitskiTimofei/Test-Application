package by.it.andersen.newsapikt.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.*
import by.it.andersen.newsapikt.data.network.ApiInterface
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class NewsRepository(articleDao: ArticleDao, apiInterface: ApiInterface) {
    private var articleList: List<Article?>? = null
    private val currentDate: String = by.it.andersen.newsapikt.presentation.util.Utils.date
    private var allNews: LiveData<List<Article?>?>? = null
    private val errorData = MutableLiveData<String?>()
    private val process = MutableLiveData<String?>()
    private val themes: MutableSet<String> = LinkedHashSet()
    private val timeToRefresh = 60000
    private val mArticleDao: ArticleDao = articleDao
    private val mApiInterface: ApiInterface = apiInterface

    val errorMessage: LiveData<String?>
        get() = errorData

    val processMessage: LiveData<String?>
        get() = process

    private fun loadNews(message: String): List<Article?>? {
        val currentTime = System.currentTimeMillis()
        if (articleList == null) {
            getData(message)
        } else {
            if (themes.size < 5 || currentTime - articleList!![0]!!.time > timeToRefresh) {
                getData(message)
            }
        }
        return articleList
    }

    @SuppressLint("CheckResult")
    fun getData(message: String): List<Article?>? {
        if (process.value != null || errorData.value != null) {
            process.value = ""
            errorData.value = ""
        }
        mApiInterface.getNewsFlowable(
            message,
            currentDate,
            PUBLISHED_AT,
            APY_KEY
        )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.filter { news: News -> refactList(news) }
            ?.map { news -> news.articles }
            ?.subscribe(object : Observer<List<Article?>?> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: ")
                }

                override fun onError(e: Throwable) {
                    errorData.value = "error"
                }

                override fun onComplete() {
                    process.postValue("stop")
                }

                override fun onNext(articles: List<Article?>) {
                    for (article in articles) {
                        article?.theme = message
                        article?.time = System.currentTimeMillis()
                    }
                    articleList = articles
                    themes.add(message)
                    Thread { mArticleDao.insert(articleList) }
                        .start()
                }
            })
        return articleList
    }

    private fun refactList(news: News): Boolean = news.articles!![0].title?.length!! > 20

    fun getAllNews(string: String): LiveData<List<Article?>?>? {
        if (articleList == null) {
            articleList = ArrayList<Article>()
        }
        loadNews(string)
        allNews = mArticleDao.getArticleByTheme(string)
        process.postValue("stop")
        return allNews
    }

    @SuppressLint("CheckResult")
    fun getSourcesRest(): Single<Sources> {
        return mApiInterface.getSources(APY_KEY)
    }

    fun getNews(title: String): Single<News> {
        return mApiInterface.getNews(title, APY_KEY)
    }

    companion object {
        private const val TAG = "REPOSITORY"
        private const val APY_KEY = "3f2b4d3e212c4c8399901475b294cbab"
        private const val PUBLISHED_AT = "publishedAt"
    }
}