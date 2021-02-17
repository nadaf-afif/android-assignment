package com.android.newsfeeds.apiInterface

import com.android.newsfeeds.model.FeedsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Afif Nadaf on 15/02/21.
 */

interface NewsFeedApiInterface {

    @GET("everything")
    fun getNewsFeeds(@Query("q") searchKey : String, @Query("apiKey") apiKey : String, @Query("page") pageNumber : Int) : Call<FeedsList>

}