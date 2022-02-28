package com.romsper.android_rick_and_morty.ui.features.characterList.adapter

import com.romsper.android_rick_and_morty.db.entities.Favorite

interface FavoriteCharacterListItemClickListener {
    fun onFavoriteListItemClickListener(item: Favorite)
    fun onRemoveFavoritesItemClickListener(item: Favorite)
}