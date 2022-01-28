package com.romsper.firebase_authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romsper.firebase_authentication.models.SingleCharacterResponse
import com.romsper.firebase_authentication.repository.AppRepository
import kotlinx.coroutines.launch

class CharacterDetailViewModel: ViewModel() {
    private val appRepository = AppRepository()

    private val _characterByIdLiveData = MutableLiveData<SingleCharacterResponse>()
    val characterById: LiveData<SingleCharacterResponse> = _characterByIdLiveData

    fun fetchCharacterById(characterId: Int) = viewModelScope.launch {
        val character = appRepository.getCharacterById(characterId = characterId)
        _characterByIdLiveData.postValue(character)
    }

//    fun fetchCharacterByIdFlow(characterId: Int) {
//        viewModelScope.launch {
//            appRepository.getCharacterByIdFlow(characterId)
//                .catch {
//                    Resource.error(data = null, message = it.message.toString())
//                }
//                .collect {
//                    it
//                }
//        }
//    }
//
//    @Deprecated("Use fetchCharacterById method")
//    fun getCharacterById(id: Int) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(Resource.success(data = appRepository.getCharacterById(characterId = id)))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }
}