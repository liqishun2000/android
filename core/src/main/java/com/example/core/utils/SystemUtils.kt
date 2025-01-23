package com.example.core.utils

import android.app.ActivityManager
import android.content.Context

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
}