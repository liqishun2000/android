package com.example.core.ktx

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.ComponentActivity

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
inline fun Context.findComponentActivity(block:(ComponentActivity)->Unit){
    findComponentActivityOrNull()?.let { block(it) }
}

tailrec fun Context.findComponentActivityOrNull():ComponentActivity?{
    return when(this){
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.findComponentActivityOrNull()
        else -> null
    }
}

//region system ui height

// 获取状态栏高度
fun Context.getStatusBarHeight(): Int {
    var height = 0
    val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        height = this.resources.getDimensionPixelSize(resourceId)
    }
    return height
}

// 获取导航栏高度
fun Context.getNavigationBarHeight(): Int {
    var height = 0
    val resourceId = this.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
        height = this.resources.getDimensionPixelSize(resourceId)
    }
    return height
}
//endregion