package com.example.android.ui.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


open class BaseViewModel<S, E>(initState: S) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initState)

    private val _effectFlow = MutableSharedFlow<E>()

    val state: S get() = _stateFlow.value

    val stateFlow = _stateFlow.asStateFlow()

    val effectFlow = _effectFlow.asSharedFlow()

    protected fun updateState(block: (S) -> S) {
        _stateFlow.update(block)
    }

    protected fun sendEffect(effect: E) {
        viewModelScope.launch {
            _effectFlow.emit(effect)
        }
    }
}

