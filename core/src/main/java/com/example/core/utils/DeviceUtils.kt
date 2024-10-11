package com.example.core.utils

import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.activity.ComponentActivity.ACCESSIBILITY_SERVICE
import com.scottyab.rootbeer.RootBeer
import com.snail.antifake.jni.EmulatorDetectUtil

object DeviceUtils {

    /** 是否无障碍  */
    fun isAccessibilityService(context: Context): Boolean {
        val am: AccessibilityManager =
            context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        return am.isEnabled
    }

    /** 是否是模拟器  */
    fun isEmulatorDevice(context: Context): Boolean {
        val isEmulator: Boolean = EmulatorDetectUtil.isEmulatorFromAll(context)
        return isEmulator
    }

    /** USB调试是否开启  */
    fun isUsbDebuggingEnabled(context: Context): Boolean {
        return try {
            // ADB_ENABLED 1是开启，0是关闭
            Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) > 0
        } catch (e: Exception) {
            false // 默认返回false
        }
    }

    /** 开发者模式是否开启  */
    fun isDeveloperOptionsEnabled(context: Context): Boolean {
        val adbEnabled = Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        )
        return adbEnabled == 1
    }

    /** 是否root  */
    fun isRooted(context: Context): Boolean {
        val rootBeer: RootBeer = RootBeer(context)
        return rootBeer.isRooted
    }

}