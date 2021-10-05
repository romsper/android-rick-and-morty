package com.romsper.firebase_authentication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.romsper.firebase_authentication.model.Result
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.repository.ContactsRepository
import com.romsper.firebase_authentication.ui.base.ContactsPagingSource
import com.romsper.firebase_authentication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ContactsViewModel: ViewModel() {
    private val appRepository = AppRepository()

    fun getCharacters() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = appRepository.getCharacters()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getCharacterById(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = appRepository.getCharacterById(characterId = id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getCharactersPaging() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ContactsPagingSource(appRepository = appRepository)
        }
    ).flow
}