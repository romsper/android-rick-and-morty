package com.romsper.android_rick_and_morty.db

import androidx.fragment.app.FragmentActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.romsper.android_rick_and_morty.db.dao.IFavoriteDao
import com.romsper.android_rick_and_morty.db.dao.IUserDao
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.db.entities.User

@Database(entities = [Favorite::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): IFavoriteDao
    abstract fun userDao(): IUserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: FragmentActivity): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rm_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}