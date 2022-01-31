package com.romsper.android_rick_and_morty.util

data class FavoritesList(var favorites: List<FavoriteItem>)

data class FavoriteItem(
    var id: Int?,
    var avatarUrl: String?,
    var name: String?
)
