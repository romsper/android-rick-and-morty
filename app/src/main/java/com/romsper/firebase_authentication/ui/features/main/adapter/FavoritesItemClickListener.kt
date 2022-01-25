package com.romsper.firebase_authentication.ui.features.main.adapter

import com.romsper.firebase_authentication.util.FavoriteItem

interface FavoritesItemClickListener {
    fun onFavoriteListItemClickListener(item: FavoriteItem)
    fun onRemoveFavoritesItemClickListener(item: FavoriteItem)
}