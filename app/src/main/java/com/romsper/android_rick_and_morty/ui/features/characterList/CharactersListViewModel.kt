package com.romsper.android_rick_and_morty.ui.features.characterList

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.models.Result
import com.romsper.android_rick_and_morty.repository.AppRepository
import com.romsper.android_rick_and_morty.ui.adapters.sources.CharacterListPagingSource
import com.romsper.android_rick_and_morty.ui.adapters.sources.SearchCharacterListPagingSource
import com.romsper.android_rick_and_morty.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class CharactersListViewModel(activity: FragmentActivity) : BaseViewModel(activity = activity) {
    private val appRepository = AppRepository(activity)

    fun fetchCharacterList(): LiveData<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            CharacterListPagingSource(appRepository = appRepository)
        }
    ).liveData.cachedIn(viewModelScope)

    fun fetchSearchCharacterList(characterName: String): LiveData<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SearchCharacterListPagingSource(
                appRepository = appRepository,
                characterName = characterName
            )
        }
    ).liveData.cachedIn(viewModelScope)

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