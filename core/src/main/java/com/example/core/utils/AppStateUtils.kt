package com.example.core.utils

import android.app.ActivityManager
import android.app.KeyguardManager
import android.app.Service
import android.content.Context
import android.os.PowerManager
import androidx.core.app.NotificationManagerCompat

object AppStateUtils {

    /**
     * 本应用是否处理前台
     */
    fun isForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Service.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcessInfoList = activityManager.runningAppProcesses ?: return false
        runningAppProcessInfoList.forEach {
            if (it.processName == context.packageName && it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
    }

    /**
     * 屏幕是否开启
     * @param context
     * @return
     */
    private fun screenOn(context: Context): Boolean {
        return (context.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive
    }

    /**
     * 是否锁屏
     * @param context
     * @return
     */
    private fun screenLock(context: Context): Boolean {
        return (context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).inKeyguardRestrictedInputMode()
    }

    /**
     * 通知栏权限是否开启
     */
    private fun isNotificationsEnabled(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}