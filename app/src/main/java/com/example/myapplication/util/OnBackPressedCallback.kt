package com.example.myapplication.util

import androidx.activity.OnBackPressedDispatcher

class OnBackPressedCallback(enabled: Boolean, val callback: OnBackPressedCallback.() -> Unit) :
    androidx.activity.OnBackPressedCallback(enabled) {
    override fun handleOnBackPressed() = callback()
}

fun OnBackPressedDispatcher.addCallback(callback: OnBackPressedCallback.() -> Unit) {
    addCallback(OnBackPressedCallback(true, callback))
}