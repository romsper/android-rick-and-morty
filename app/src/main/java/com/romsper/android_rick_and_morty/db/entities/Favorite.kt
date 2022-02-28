package com.romsper.android_rick_and_morty.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "character_id") val characterId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String
)