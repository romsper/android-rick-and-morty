package com.romsper.firebase_authentication.util

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {
    lateinit var binding: B
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences

    lateinit var favoriteItem: FavoriteItem
    lateinit var existingIds: String

    val gson: Gson = Gson()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        firebaseAuth = FirebaseAuth.getInstance()

        sharedPreferences = getSharedPreferences("PREF_FAVORITES", MODE_PRIVATE)
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