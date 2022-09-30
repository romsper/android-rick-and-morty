package com.romsper.android_rick_and_morty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romsper.android_rick_and_morty.db.dao.IFavoriteDao
import com.romsper.android_rick_and_morty.db.dao.IUserDao
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.db.entities.User

@Database(entities = [Favorite::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDao: IFavoriteDao
    abstract val userDao: IUserDao
}