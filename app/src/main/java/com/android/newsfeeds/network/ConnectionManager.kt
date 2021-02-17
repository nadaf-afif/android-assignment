package com.android.newsfeeds.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Created by Afif Nadaf on 14/02/21.
 */

class ConnectionManager {

    companion object{

        fun isConnected(context: Context) : Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                    else ->     false
                }
            } else if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
            return false
        }
    }


}