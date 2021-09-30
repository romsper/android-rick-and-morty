package com.romsper.firebase_authentication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.repository.ContactsRepository
import com.romsper.firebase_authentication.util.Resource
import kotlinx.coroutines.Dispatchers

class ContactsViewModal(private val appRepository: AppRepository) : ViewModel() {

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

}