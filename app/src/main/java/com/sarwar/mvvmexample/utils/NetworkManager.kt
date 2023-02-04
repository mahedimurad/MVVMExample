package com.sarwar.mvvmexample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkManager(private val context: Context) {

    fun isNetworkConnected(): Boolean {
        var result = false

        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            val activeNetwork = cm.activeNetwork?: return false
            val networkCapabilities = cm.getNetworkCapabilities(activeNetwork)?: return false

            result = when{
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
                else -> false
            }

        }else{
            cm.activeNetworkInfo?.run {
                result = when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }
}