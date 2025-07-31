package com.example.android.training.floatingWindow

/**
 * 沉浸式 悬浮窗沉浸式 WindowManager.LayoutParams设置：
 *
 * if (Build.VERSION.SDK_INT >= 28) {
 *    params.layoutInDisplayCutoutMode =
 *    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
 * }
 *
 * view 设置:
 *  systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
 *             View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
 *             View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
 * */

/**
 * 悬浮窗 EditText
 *
 * 移除flag:WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
 * 否则无法获取焦点
 *
 * params设置,类同Activity
 * params.apply {
 *  softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or
 *  WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
 * }
 *
 * view 设置：
 * this.fitsSystemWindows = true
 * */