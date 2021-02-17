package com.android.newsfeeds.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.newsfeeds.model.NewsFeed
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Afif Nadaf on 18/02/21.
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsFeedDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var dao: NewsFeedDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), NewsDatabase::class.java)
            .allowMainThreadQueries().build()

        dao = database.newsFeedDao()
    }


    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertNewsItem(){
        val newItem = NewsFeed(1, "author", "title", "description", "", "223232", "Content")
        dao.insertNewsFeed(newItem)

        val feedsList = dao.getNewsFeeds()
        Truth.assertThat(feedsList).contains(newItem)
    }


}