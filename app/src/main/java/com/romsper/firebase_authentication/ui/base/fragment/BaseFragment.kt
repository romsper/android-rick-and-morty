package com.romsper.firebase_authentication.ui.base.fragment

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.romsper.firebase_authentication.util.FavoriteItem
import java.lang.reflect.Type

abstract class BaseFragment(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    val gson: Gson = Gson()
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    lateinit var favoriteItem: FavoriteItem
    lateinit var existingIds: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = activity!!.getSharedPreferences("PREF_FAVORITES", AppCompatActivity.MODE_PRIVATE)

        FirebaseCrashlytics.getInstance().log(
            this.javaClass.simpleName
        )
    }

    fun getFavorites(): List<FavoriteItem> {
        val jsonFavorites = "[${sharedPreferences.getString("KEY_FAVORITES", "")}]"
        val jsonFavoritesArray = JsonParser.parseString(jsonFavorites).asJsonArray
        val listType: Type = object : TypeToken<ArrayList<FavoriteItem?>?>() {}.type
        return gson.fromJson(jsonFavoritesArray, listType)
    }

    fun removeFavoriteItem(item: FavoriteItem) {
        favoriteItem = FavoriteItem(
            id = item.id,
            avatarUrl = item.avatarUrl,
            name = item.name
        )

        val favorites = getFavorites().toMutableList()
        if (favorites.size == 1) {
            favorites.clear()
            sharedPreferences.edit().putString(
                "KEY_FAVORITES", ""
            ).apply()
        } else {
            favorites.remove(favoriteItem)
            sharedPreferences.edit().putString(
                "KEY_FAVORITES",
                gson.toJson(favorites).toString().replace("[", "").replace("]", "")
            ).apply()
        }
    }
}