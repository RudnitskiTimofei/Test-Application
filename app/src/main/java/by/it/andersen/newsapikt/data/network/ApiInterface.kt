package by.it.andersen.newsapikt.data.network

import by.it.andersen.newsapikt.data.model.News
import by.it.andersen.newsapikt.data.model.Sources
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/everything")
    fun getNewsFlowable(
        @Query("q") source: String?,
        @Query("from") from: String?,
        @Query("sortBy") sort: String?,
        @Query("apiKey") apiKey: String?
    ): Observable<News?>?

    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey") apiKey: String?
    ): Single<Sources>

    @GET("v2/everything")
    fun getNews(
        @Query("q") source: String?,
        @Query("apiKey") apiKey: String?
    ): Single<News>
}