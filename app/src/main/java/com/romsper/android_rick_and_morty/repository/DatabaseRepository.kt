package com.romsper.android_rick_and_morty.repository

import android.content.Context
import com.romsper.android_rick_and_morty.db.AppDatabase.Companion.getAppDatabase
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.db.entities.User

class DatabaseRepository {

    //Favorites
//    suspend fun getFavorites(context: Context) = getAppDatabase(context = context).favoriteDao().getFavoriteCharacters()
//    suspend fun addFavorite(favorite: Favorite, context: Context) = getAppDatabase(context = context).favoriteDao().addFavoriteCharacter(favorite = favorite)
//    suspend fun removeFavoriteCharacter(characterId: Int, context: Context) = getAppDatabase(context = context).favoriteDao().removeFavoriteCharacter(characterId = characterId)

    //Users
//    suspend fun getUsers(context: Context) = getAppDatabase(context = context).userDao().getUsers()
//    suspend fun getUserByEmail(email: String, context: Context) = getAppDatabase(context = context).userDao().getUserByEmail(email = email)
//    suspend fun addUser(user: User, context: Context) = getAppDatabase(context = context).userDao().addUser(user = user)
//    suspend fun removeUsers(context: Context) = getAppDatabase(context = context).userDao().removeUsers()
}