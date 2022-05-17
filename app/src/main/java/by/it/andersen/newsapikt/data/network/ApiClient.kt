package by.it.andersen.newsapikt.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiClient{

    companion object{
        val BASE_URL:String = "http://newsapi.org/"
        var retrofit:Retrofit? = null
        val interceptor:HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client:OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        fun getClient():Retrofit?{
            if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            }
            return retrofit;
        }

        private var apiInterface:ApiInterface = getClient()!!.create(ApiInterface::class.java)
        fun getApiInterface():ApiInterface{
            return apiInterface;
        }

    }
}