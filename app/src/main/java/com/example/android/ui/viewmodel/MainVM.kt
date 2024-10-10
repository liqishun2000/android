package com.example.android.ui.viewmodel

import com.example.android.ui.viewmodel.base.BaseViewModel

object MainVM:BaseViewModel<MainVM.State,Unit>(State()) {


    fun updateShake(shake: Boolean){
        updateState {
            it.copy(shake = shake)
        }
    }

    data class State(
        val shake:Boolean = false
    )
}