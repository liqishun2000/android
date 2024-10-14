package com.example.android.ui.viewmodel

import android.app.Activity
import android.view.accessibility.AccessibilityManager
import androidx.activity.ComponentActivity.ACCESSIBILITY_SERVICE
import com.example.android.ui.viewmodel.base.BaseViewModel

object MainVM:BaseViewModel<MainVM.State,Unit>(State()) {

    fun click(activity: Activity){

    }

    fun updateShake(shake: Boolean){
        updateState {
            it.copy(shake = shake)
        }
    }

    fun updateStepCount(stepCount: Int){
        updateState { it.copy(stepCount = stepCount) }
    }

    data class State(
        val shake:Boolean = false,
        val stepCount:Int = 0,
    )
}