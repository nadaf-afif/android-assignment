package com.android.newsfeeds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.newsfeeds.datasource.NewsFeedsRepository
import com.android.newsfeeds.model.ErrorStatus
import com.android.newsfeeds.model.NewsFeed
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Afif Nadaf on 14/02/21.
 */
@HiltViewModel
class NewsFeedsViewModel  @Inject constructor(private val newsFeedsRepository: NewsFeedsRepository) : ViewModel() {

    private val _newsFeedResponse = MutableLiveData<List<NewsFeed>>()
    val newsFeedsResponse : LiveData<List<NewsFeed>> = _newsFeedResponse

    private val _newsFeedError = MutableLiveData<ErrorStatus>()
    val newsFeedError : LiveData<ErrorStatus> = _newsFeedError

    private val _newsFeed = MutableLiveData<NewsFeed>()
    val newsFeedDetail : LiveData<NewsFeed> = _newsFeed

    val pageNumber = MutableLiveData<Int>()




    fun fetchNewsFeeds(pageNumber : Int){
        newsFeedsRepository.getNewsFeeds(pageNumber,
            { response ->  _newsFeedResponse.value = response },
            { error -> _newsFeedError.value = error}
        )
    }

    fun fetchFeedDetails(rowId : Int){
        val feedDetails =  newsFeedsRepository.getFeedDetailsById(rowId)
        _newsFeed.value = feedDetails
    }

}