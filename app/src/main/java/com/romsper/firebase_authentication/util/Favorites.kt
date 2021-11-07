package com.romsper.firebase_authentication.util

data class FavoritesList(var favorites: List<FavoriteItem>)

data class FavoriteItem(
    var id: Int?,
    var avatarUrl: String?,
    var name: String?
)
