package by.it.andersen.newsapikt.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sources {
    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("sources")
    @Expose
    val sources: List<Categories>? = null
}


class Categories {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
}
