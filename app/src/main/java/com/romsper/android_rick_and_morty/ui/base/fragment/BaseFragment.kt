package com.romsper.android_rick_and_morty.ui.base.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

abstract class BaseFragment(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    val gson: Gson = Gson()
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        firebaseAuth = FirebaseAuth.getInstance()
    }
}