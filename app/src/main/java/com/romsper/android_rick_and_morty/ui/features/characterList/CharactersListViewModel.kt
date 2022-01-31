package com.romsper.android_rick_and_morty.ui.features.characterList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.romsper.android_rick_and_morty.models.Result
import com.romsper.android_rick_and_morty.repository.AppRepository
import com.romsper.android_rick_and_morty.ui.adapters.sources.CharacterListPagingSource
import com.romsper.android_rick_and_morty.ui.adapters.sources.SearchCharacterListPagingSource

class CharactersListViewModel : ViewModel() {
    private val appRepository = AppRepository()

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

//    fun fetchCharacterList() = Pager(
//        config = PagingConfig(
//            pageSize = 10,
//            enablePlaceholders = false
//        ),
//        pagingSourceFactory = {
//            CharacterListPagingSource(appRepository = appRepository)
//        }
//    ).flow.cachedIn(viewModelScope)
//
//    fun fetchSearchCharacterList(characterName: String) = Pager(
//        config = PagingConfig(
//            pageSize = 10,
//            enablePlaceholders = false
//        ),
//        pagingSourceFactory = {
//            SearchCharacterListPagingSource(appRepository = appRepository, characterName = characterName)
//        }
//    ).flow.cachedIn(viewModelScope)
}