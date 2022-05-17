package by.it.andersen.newsapikt.data.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "articles",
    indices = [Index(value = ["description"], unique = true)]
)
class Article {
    @PrimaryKey(autoGenerate = true)
    var idArticle: Long? = null

    @ColumnInfo(name = "theme")
    var theme: String? = null

    @ColumnInfo(name = "time")
    var time: Long = 0

    @Embedded
    @SerializedName("source")
    @Expose
    var source: Source? = null

    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    var author: String? = null

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String? = null

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    var description: String? = null

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = null

    constructor(article: Article?) {}
    constructor(
        source: Source?,
        author: String?,
        title: String?,
        description: String?,
        url: String?,
        urlToImage: String?,
        publishedAt: String?
    ) {
        this.source = source
        this.author = author
        this.title = title
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
        this.publishedAt = publishedAt
    }
}