package com.romsper.android_rick_and_morty.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)

fun Context.appToast(msg: CharSequence, isShort: Boolean = true) {
    if (!isShort) Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    else Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.appToast(msg: CharSequence, isShort: Boolean = true) {
    if (!isShort) Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    else Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.inVisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}