package com.example.core.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

object WindowUtils {

    //window 沉浸式状态栏
    @JvmStatic
    fun fullScreen(window:Window) {
        window.decorView.systemUiVisibility = getSystemUiVisibility()

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        val layoutParams = window.attributes //获取dialog布局的参数

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT //全屏

        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT //全屏

        //设置导航栏颜
        window.navigationBarColor = Color.TRANSPARENT
        //内容扩展到导航栏
        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.attributes = layoutParams
    }

    private fun getSystemUiVisibility(): Int {
        return View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
    //endregion


}