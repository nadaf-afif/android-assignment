package com.android.newsfeeds.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.newsfeeds.database.NewsDatabase

/**
 * Created by Afif Nadaf on 14/02/21.
 */
@Keep
@Entity(tableName = NewsDatabase.TABLE_NAME)
data class NewsFeed(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "author", ) val author : String?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "description") val description : String?,
    @ColumnInfo(name = "urlToImage") val urlToImage : String?,
    @ColumnInfo(name = "publishedAt") val publishedAt : String?,
    @ColumnInfo(name = "content") val content : String?
)

@Keep
data class FeedsList(
    val status : String,
    val totalResults : Int,
    val articles : List<NewsFeed>,
    val code : String
)