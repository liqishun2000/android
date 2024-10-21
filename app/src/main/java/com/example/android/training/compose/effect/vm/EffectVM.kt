package com.example.android.training.compose.effect.vm

import com.example.android.ui.viewmodel.base.BaseViewModel

object EffectVM : BaseViewModel<EffectVM.State, Unit>(State()) {

    fun updateState(show: Boolean = true, key: Int = 0) {
        updateState {
            it.copy(show = show, key = key)
        }
    }

    data class State(
        val show: Boolean = true,
        val key: Int = 0,
    )
}