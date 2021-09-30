package com.romsper.firebase_authentication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.romsper.firebase_authentication.model.CharactersResponse
import retrofit2.Response

//object Utils {
//    fun hasInternetConnection(application: Response<CharactersResponse>): Boolean {
//        val connectivityManager = application.getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }
//}