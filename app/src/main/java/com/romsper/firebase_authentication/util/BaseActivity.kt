package com.romsper.firebase_authentication.util

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {
    lateinit var binding: B
    lateinit var mAuth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        sharedPreferences = getSharedPreferences("PREF_FAVORITES", MODE_PRIVATE)
    }

    fun getFavorites(): List<FavoriteItem> {
        val jsonFavorites = "[${sharedPreferences.getString("KEY_FAVORITES", "")}]"
        val jsonFavoritesArray = JsonParser.parseString(jsonFavorites).asJsonArray
        val listType: Type = object : TypeToken<List<FavoriteItem?>?>() {}.type
        return gson.fromJson(jsonFavoritesArray, listType)
    }
}