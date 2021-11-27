package com.romsper.firebase_authentication.ui.character.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.util.Resource
import kotlinx.coroutines.Dispatchers

class CharacterViewModel: ViewModel() {
    private val appRepository = AppRepository()

    fun getCharacterById(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = appRepository.getCharacterById(characterId = id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}