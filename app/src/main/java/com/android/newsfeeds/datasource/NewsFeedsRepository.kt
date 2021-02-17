package com.android.newsfeeds.datasource

import android.content.Context
import com.android.newsfeeds.R
import com.android.newsfeeds.apiInterface.NewsFeedApiInterface
import com.android.newsfeeds.database.NewsFeedDao
import com.android.newsfeeds.model.ErrorStatus
import com.android.newsfeeds.model.FeedsList
import com.android.newsfeeds.model.NewsFeed
import com.android.newsfeeds.network.ConnectionManager
import com.android.newsfeeds.utils.Constants
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Afif Nadaf on 14/02/21.
 */
@ActivityScoped
class NewsFeedsRepository @Inject constructor(
    private val newsFeedDao: NewsFeedDao,
    private val newsFeedApiInterface: NewsFeedApiInterface,
    private val mContext: Context
) {

    fun getNewsFeeds(
        pageNumber: Int,
        success: (response: List<NewsFeed>) -> Unit,
        failed: (error: ErrorStatus) -> Unit
    ) {
        when {
            ConnectionManager.isConnected(mContext) -> {

                val apiResponse = newsFeedApiInterface.getNewsFeeds(
                    Constants.SEARCH_KEY,
                    Constants.NEWS_API_KEY,
                    pageNumber
                )

                apiResponse.enqueue(object : Callback<FeedsList> {
                    override fun onResponse(call: Call<FeedsList>, date: Response<FeedsList>) {

                        val response = date.body()

                        when {
                            response?.status == Constants.OK -> {
                                addNewsFeedsInLocalDB(pageNumber, response.articles)
                                val feedsList = newsFeedDao.getNewsFeeds()
                                success.invoke(feedsList)
                            }
                            pageNumber == 1 && newsFeedDao.getNewsFeeds().isNotEmpty() -> {
                                val newsFeedList = newsFeedDao.getNewsFeeds()
                                success.invoke(newsFeedList)
                            }
                            else -> {
                                if (response?.code.isNullOrBlank()) {
                                    failed.invoke(
                                        ErrorStatus(
                                            426,
                                            response?.code?: mContext.getString(R.string.something_went_wrong)
                                        )
                                    )
                                } else {
                                    failed.invoke(
                                        ErrorStatus(0, mContext.getString(R.string.something_went_wrong))
                                    )
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<FeedsList>, t: Throwable) {
                        failed.invoke(
                            ErrorStatus(
                                0,
                                mContext.getString(R.string.please_check_internet_connection)
                            )
                        )
                    }

                })


            }
            newsFeedDao.getNewsFeeds().isNotEmpty() -> {
                val newsFeedList = newsFeedDao.getNewsFeeds()
                success.invoke(newsFeedList)
            }

            else -> {
                failed.invoke(
                    ErrorStatus(
                        0,
                        mContext.getString(R.string.please_check_internet_connection)
                    )
                )
            }
        }
    }

    private fun addNewsFeedsInLocalDB(pageNumber: Int, articles: List<NewsFeed>) {
        if (pageNumber == 1) {
            newsFeedDao.deleteAllRecords()
        }
        for (feeds in articles) {
            newsFeedDao.insertNewsFeed(feeds)
        }
    }

    fun getFeedDetailsById(rowId: Int) : NewsFeed {
        return newsFeedDao.getFeedsDetails(rowId)
    }

}