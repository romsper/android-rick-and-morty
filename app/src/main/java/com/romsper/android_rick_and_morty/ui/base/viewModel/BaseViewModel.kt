package com.romsper.android_rick_and_morty.ui.base.viewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romsper.android_rick_and_morty.db.AppDatabase
import com.romsper.android_rick_and_morty.db.entities.Favorite
import kotlinx.coroutines.launch

open class BaseViewModel(activity: FragmentActivity): ViewModel() {

}