package com.romsper.android_rick_and_morty.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.romsper.android_rick_and_morty.db.entities.Favorite

@Dao
interface IFavoriteDao {

    @Query("SELECT * FROM favorites_table ORDER BY name ASC")
    suspend fun getFavoriteCharacters(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteCharacter(favorite: Favorite)

    @Query("DELETE FROM favorites_table WHERE character_id = :characterId")
    suspend fun removeFavoriteCharacter(characterId: Int)
}