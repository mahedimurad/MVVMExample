package com.sarwar.mvvmexample.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

class NetworkBroadcastReceiver(context: Context,val callbacks: (isConnected: Boolean)-> Unit) : BroadcastReceiver() {
    private var networkManager: NetworkManager = NetworkManager(context)
    override fun onReceive(context: Context?, intent: Intent?) {
        callbacks.invoke(networkManager.isNetworkConnected())
    }
}