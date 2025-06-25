package com.example.core.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import java.util.Locale

object SystemUtils {

    /** 主进程 */
    fun isMainProcess(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        if (runningAppProcesses != null) {
            for (processInfo in runningAppProcesses) {
                if (processInfo.pid == android.os.Process.myPid()) {
                    if (context.packageName == processInfo.processName) return true
                }
            }
        }
        return false
    }

    /** 摩托罗拉设备 */
    fun isMotorolaDevice(): Boolean {
        return try {
            Build.MANUFACTURER.lowercase().contains("motorola");
        } catch (e: Exception) {
            false;
        }
    }
}