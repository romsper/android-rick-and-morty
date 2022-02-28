package com.romsper.android_rick_and_morty.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.romsper.android_rick_and_morty.db.entities.User

@Dao
interface IUserDao {

    @Query("SELECT * FROM users_table")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM users_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("DELETE FROM users_table")
    suspend fun removeUsers()
}