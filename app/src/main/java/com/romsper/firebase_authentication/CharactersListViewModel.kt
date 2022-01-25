package com.romsper.firebase_authentication

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.ui.adapters.sources.CharactersListPagingSource
import com.romsper.firebase_authentication.ui.adapters.sources.SearchCharactersPagingSource

class CharactersListViewModel: ViewModel() {
    private val appRepository = AppRepository()

    fun getCharactersPaging() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            CharactersListPagingSource(appRepository = appRepository)
        }
    ).flow

    fun searchCharactersPaging(characterName: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SearchCharactersPagingSource(appRepository = appRepository, characterName = characterName)
        }
    ).flow
}