package com.android.newsfeeds

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Afif Nadaf on 15/02/21.
 */
@HiltAndroidApp
class NewsFeedApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}