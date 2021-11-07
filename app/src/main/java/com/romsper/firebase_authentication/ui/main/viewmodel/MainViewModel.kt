package com.romsper.firebase_authentication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.ui.base.ContactsPagingSource

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
}