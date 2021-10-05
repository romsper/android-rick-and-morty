package com.romsper.firebase_authentication.ui.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.util.Resource
import kotlinx.coroutines.Dispatchers

class ExampleViewModel: ViewModel() {

    fun getCharacters() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = AppRepository().getCharacters()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}