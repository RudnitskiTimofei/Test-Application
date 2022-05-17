package by.it.andersen.newsapikt.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import by.it.andersen.newsapikt.data.model.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAll(): LiveData<List<Article?>?>?

    @Query("SELECT * FROM articles WHERE theme = :topic  ORDER BY publishedAt DESC ")
    fun getArticleByTheme(topic: String?): LiveData<List<Article?>?>?

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllTest(): List<Article?>?

    @Query("SELECT * FROM articles WHERE theme = :topic  ORDER BY publishedAt DESC ")
    fun getArticleTest(topic: String?): List<Article?>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(articles: List<Article?>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserArticle(article: Article?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(articles: List<Article?>?)

    @Delete
    fun delete(article: Article?)

    @Query("DELETE FROM articles ")
    fun deleteAll()
}