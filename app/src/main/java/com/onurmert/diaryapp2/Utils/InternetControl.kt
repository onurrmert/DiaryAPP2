package com.onurmert.retro4fitt.Utils

import android.content.Context
import android.net.ConnectivityManager

class InternetControl() {

    companion object{

        fun connectionControl(context: Context) : Boolean {
            val manager =
                context.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo

            if (networkInfo == null) {
                return false
            } else {
                return true
            }
        }
    }
}