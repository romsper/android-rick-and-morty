package com.romsper.android_rick_and_morty.ui.features.characterList.adapter

import com.romsper.android_rick_and_morty.util.FavoriteItem

interface FavoriteCharacterListItemClickListener {
    fun onFavoriteListItemClickListener(item: FavoriteItem)
    fun onRemoveFavoritesItemClickListener(item: FavoriteItem)
}