package com.romsper.android_rick_and_morty.ui.features.characterDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.models.SingleCharacterResponse
import com.romsper.android_rick_and_morty.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _characterByIdLiveData = MutableLiveData<SingleCharacterResponse>()
    val characterById: LiveData<SingleCharacterResponse> = _characterByIdLiveData

    fun fetchCharacterById(characterId: Int) = viewModelScope.launch {
        val character = appRepository.getCharacterById(characterId = characterId)
        _characterByIdLiveData.postValue(character)
    }

    private val _favoriteListLiveData = MutableLiveData<List<Favorite>>()
    val favoriteList: LiveData<List<Favorite>> = _favoriteListLiveData

    fun fetchFavorites() = viewModelScope.launch {
        val favoriteList = appRepository.getFavoritesDB()
        _favoriteListLiveData.postValue(favoriteList)
    }

    fun addFavorite(favorite: Favorite) = viewModelScope.launch {
        appRepository.addFavoriteDB(favorite = favorite)
    }

    fun removeFavoriteItem(characterId: Int) = viewModelScope.launch {
        appRepository.removeFavoriteDB(characterId = characterId)
    }
}