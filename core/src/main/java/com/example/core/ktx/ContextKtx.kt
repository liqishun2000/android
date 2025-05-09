package com.example.core.ktx

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

//region 屏幕高度

//绝对高度
fun Context.getScreenHeight(): Int {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // API 30+ 使用 WindowMetrics
        val windowMetrics = windowManager.currentWindowMetrics
        windowMetrics.bounds.height()
    } else {
        // 旧版本使用 DisplayMetrics
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

//可用高度
fun getScreenDisplayHeight() =  Resources.getSystem().displayMetrics.heightPixels

//endregion