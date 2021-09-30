package com.romsper.firebase_authentication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romsper.firebase_authentication.repository.AppRepository
import com.romsper.firebase_authentication.ui.main.viewmodel.ContactsViewModal

class ContactsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModal::class.java)) {
            return ContactsViewModal(AppRepository()) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}