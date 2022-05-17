package by.it.andersen.newsapikt.di.modules

import android.app.Application
import androidx.room.Room
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.datasource.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(application: Application?): NewsDatabase {
        return Room.databaseBuilder<NewsDatabase>(
            application!!,
            NewsDatabase::class.java,
            "NewsDataBase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(database: NewsDatabase): ArticleDao {
        return database.article()!!
    }
}