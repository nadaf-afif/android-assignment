package com.android.newsfeeds.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.newsfeeds.model.NewsFeed
import java.sql.RowId

/**
 * Created by Afif Nadaf on 14/02/21.
 */

@Dao
interface NewsFeedDao{

    @Query("SELECT * FROM ${NewsDatabase.TABLE_NAME}")
    fun getNewsFeeds() : List<NewsFeed>

    @Insert
    fun insertNewsFeed(newsFeed: NewsFeed)

    @Query("DELETE FROM ${NewsDatabase.TABLE_NAME}")
    fun deleteAllRecords()

    @Query("SELECT * FROM ${NewsDatabase.TABLE_NAME} WHERE id = :rowId")
    fun getFeedsDetails(rowId: Int) : NewsFeed

}