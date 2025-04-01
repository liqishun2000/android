package com.example.core.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

object KeyboardUtils {

    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus ?: activity.window.decorView
        hideKeyboard(activity, view)
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}