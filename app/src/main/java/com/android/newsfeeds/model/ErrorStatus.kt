package com.android.newsfeeds.model

import androidx.annotation.Keep

/**
 * Created by Afif Nadaf on 15/02/21.
 */
@Keep
data class ErrorStatus(
    private val status : Int,
    private val message : String
)