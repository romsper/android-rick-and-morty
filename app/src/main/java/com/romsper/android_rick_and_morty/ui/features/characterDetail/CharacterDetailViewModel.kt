package com.romsper.android_rick_and_morty.ui.features.characterDetail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.models.SingleCharacterResponse
import com.romsper.android_rick_and_morty.repository.AppRepository
import com.romsper.android_rick_and_morty.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class CharacterDetailViewModel(activity: FragmentActivity) : BaseViewModel(activity = activity)  {
    private val appRepository = AppRepository(activity)

    private val _characterByIdLiveData = MutableLiveData<SingleCharacterResponse>()
    val characterById: LiveData<SingleCharacterResponse> = _characterByIdLiveData

    fun fetchCharacterById(characterId: Int) = viewModelScope.launch {
        val character = appRepository.getCharacterById(characterId = characterId)
        _characterByIdLiveData.postValue(character)
    }

    private val _favoriteListLiveData = MutableLiveData<List<Favorite>>()
    val favoriteList: LiveData<List<Favorite>> = _favoriteListLiveData

    fun fetchFavorites() = viewModelScope.launch {
        val favoriteList = appRepository.database.favoriteDao().getFavoriteCharacters()
        _favoriteListLiveData.postValue(favoriteList)
    }

    fun addFavorite(favorite: Favorite) = viewModelScope.launch {
        appRepository.database.favoriteDao().addFavoriteCharacter(favorite = favorite)
    }

    fun removeFavoriteItem(characterId: Int) = viewModelScope.launch {
        appRepository.database.favoriteDao().removeFavoriteCharacter(characterId = characterId)
    }
}