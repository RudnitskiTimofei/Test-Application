package by.it.andersen.newsapikt.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Source(
    @field:ColumnInfo(name = "sourceId")
    @field:Expose
    @field:SerializedName("id")
    var id: String?,
    @field:ColumnInfo(
        name = "name"
    ) @field:Expose
    @field:SerializedName("name")
    var name: String,
)
