package com.romsper.android_rick_and_morty.di

import android.content.Context
import androidx.room.Room
import com.romsper.android_rick_and_morty.db.AppDatabase
import com.romsper.android_rick_and_morty.db.dao.IFavoriteDao
import com.romsper.android_rick_and_morty.db.dao.IUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "rm_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: AppDatabase): IFavoriteDao = database.favoriteDao

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): IUserDao = database.userDao
}