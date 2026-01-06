package com.example.android.training.multipleLanguage

import android.view.View

/**
 * 多语言中layoutDirection不同
 * */

fun View.fitDirection(){
    if(this.layoutDirection == View.LAYOUT_DIRECTION_RTL){
        this.scaleX = -1f
    }else{
        this.scaleX = 1f
    }
}