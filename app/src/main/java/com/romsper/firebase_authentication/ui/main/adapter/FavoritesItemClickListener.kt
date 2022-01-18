package com.romsper.firebase_authentication.ui.main.adapter

import android.view.View
import com.romsper.firebase_authentication.util.FavoriteItem

interface FavoritesItemClickListener {
    fun onFavoritesItemClickListener(item: FavoriteItem)
    fun onRemoveFavoritesItemClickListener(item: FavoriteItem)
}