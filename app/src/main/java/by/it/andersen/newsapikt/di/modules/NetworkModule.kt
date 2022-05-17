package by.it.andersen.newsapikt.di.modules

import by.it.andersen.newsapikt.data.network.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NewsViewModelModule::class])
interface NetworkModule {
    companion object {
        @Provides
        @Singleton
        fun provideApiInterface(retrofit: Retrofit): ApiInterface {
            return retrofit.create<ApiInterface>(ApiInterface::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofitInstance(okHttpClient: OkHttpClient?): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideHttpLogginInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return interceptor
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor!!)
                .build()
        }
    }
}