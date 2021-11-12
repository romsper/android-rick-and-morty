package com.romsper.firebase_authentication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.ui.base.ContactsPagingSource
import com.romsper.firebase_authentication.ui.base.SearchCharactersPagingSource
import com.romsper.firebase_authentication.util.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel: ViewModel() {
    private val appRepository = AppRepository()

    fun getCharactersPaging() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ContactsPagingSource(appRepository = appRepository)
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

    fun searchCharacters(characterName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = AppRepository().searchCharacters(characterName = characterName, page = 1)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}