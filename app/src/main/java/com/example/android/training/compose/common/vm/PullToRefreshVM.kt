package com.example.android.training.compose.common.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.android.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PullToRefreshVM : BaseViewModel<PullToRefreshVM.State, Unit>(State()) {

    fun onRefresh() {
        Log.d("...", "开始刷新")
        updateRefreshState(true)
        viewModelScope.launch {
            delay(1000)
            updateRefreshState(false)


        }

    }

    private fun updateRefreshState(isRefresh: Boolean) {
        updateState { it.copy(isRefresh = isRefresh) }
    }


    data class State(
        val isRefresh: Boolean = false
    )
}