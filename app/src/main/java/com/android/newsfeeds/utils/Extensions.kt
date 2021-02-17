package com.android.newsfeeds.utils

import android.view.View

/**
 * Created by Afif Nadaf on 15/02/21.
 */


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}