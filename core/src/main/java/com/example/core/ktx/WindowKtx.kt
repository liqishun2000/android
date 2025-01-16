package com.example.core.ktx

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat


//region 沉浸式状态栏
fun Window.fullScreen() {
    this.decorView.systemUiVisibility = getSystemUiVisibility()

    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.statusBarColor = Color.TRANSPARENT
    val layoutParams = this.attributes //获取dialog布局的参数

    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT //全屏

    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT //全屏

    //设置导航栏颜
    this.navigationBarColor = Color.TRANSPARENT
    //内容扩展到导航栏
    this.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
    if (Build.VERSION.SDK_INT >= 28) {
        layoutParams.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
    this.attributes = layoutParams
}

private fun getSystemUiVisibility(): Int {
    return View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}
//endregion

//region 状态栏颜色
fun Window.setStatusBarDark(isDark: Boolean = true) {
    if (isDark) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = Color.TRANSPARENT
    } else {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = Color.TRANSPARENT
    }
}

/** 在enableEdgeToEdge方法后调用 */
fun Window.setStatusBarDarkNew(isDark: Boolean = true) {
    WindowCompat.getInsetsController(this,this.decorView).isAppearanceLightStatusBars = isDark
}
//endregion

