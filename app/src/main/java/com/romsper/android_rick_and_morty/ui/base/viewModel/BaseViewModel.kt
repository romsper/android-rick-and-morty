package com.romsper.android_rick_and_morty.ui.base.viewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romsper.android_rick_and_morty.db.AppDatabase
import com.romsper.android_rick_and_morty.db.entities.Favorite
import kotlinx.coroutines.launch

open class BaseViewModel(activity: FragmentActivity): ViewModel() {
    private val appDatabase = AppDatabase.getAppDatabase(activity)

    private val _favoriteListLiveData = MutableLiveData<List<Favorite>>()
    val favoriteList: LiveData<List<Favorite>> = _favoriteListLiveData

    fun fetchFavorites() = viewModelScope.launch {
        val favoriteList = appDatabase.favoriteDao().getFavoriteCharacters()
        _favoriteListLiveData.postValue(favoriteList)
    }

    fun addFavorite(favorite: Favorite) = viewModelScope.launch {
        appDatabase.favoriteDao().addFavoriteCharacter(favorite = favorite)
    }

    fun removeFavoriteItem(characterId: Int) = viewModelScope.launch {
        appDatabase.favoriteDao().removeFavoriteCharacter(characterId = characterId)
    }

}