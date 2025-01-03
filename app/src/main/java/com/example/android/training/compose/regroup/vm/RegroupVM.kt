package com.example.android.training.compose.regroup.vm

import com.example.android.ui.viewmodel.base.BaseViewModel

class RegroupVM:BaseViewModel<RegroupVM.State,Unit>(State()){

    fun updateIntValue(value:Int){
        updateState { it.copy(intValue = value) }
    }

    fun addIntValue(){
        updateState {
            it.copy(
                intValue = it.intValue+1,
                str = (it.intValue+1).toString()
            )
        }
    }

    data class State(
        val intValue:Int = 0,
        val str:String =  "",
    )
}