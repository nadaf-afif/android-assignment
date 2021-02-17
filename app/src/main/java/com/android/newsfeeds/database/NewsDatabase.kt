package com.android.newsfeeds.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.android.newsfeeds.model.NewsFeed

/**
 * Created by Afif Nadaf on 14/02/21.
 */

@Database(entities = [NewsFeed::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {


    companion object{
        const val DATABASE_NAME = "news_feeds_db"
        const val TABLE_NAME = "news_feeds"
    }

    abstract fun newsFeedDao() : NewsFeedDao

}