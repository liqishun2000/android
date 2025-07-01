package com.example.android.training.locked_screen

/**
 * 锁屏显示UI
 * 清单文件添加android:showWhenLocked="true"
 * 应用外显示还需要添加android.permission.SYSTEM_ALERT_WINDOW权限
 * 在activity里设置flag:
 *         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
 *             setShowWhenLocked(true)
 *         } else {
 *             window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
 *         }
 * 这两个广播需要动态注册才能收到：
 * addAction(Intent.ACTION_SCREEN_ON)
 * addAction(Intent.ACTION_SCREEN_OFF)
 *
 * 适配问题:
 * 如果弹不出来，系统会有通知需要打开什么权限，例如:
 * vivo需要：background pop-ups 和 Display on lock screen权限
 * */