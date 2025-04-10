package com.example.core.ktx

import android.content.Context
import android.util.TypedValue

/**
 * TypedValue.applyDimension 其他单位转为px 如dp,px
 * */
fun Float.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    ).toInt()
}
