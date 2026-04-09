package com.example.android

import android.app.Application
import com.example.core.ktx.log
import com.example.core.utils.ActivityStackUtils
import com.example.core.utils.AppStateUtils
import dagger.hilt.android.HiltAndroidApp

// 第①步：Application 必须加 @HiltAndroidApp，这是 Hilt 的总开关
@HiltAndroidApp
class App:Application() {

    override fun onCreate() {
        super.onCreate()
        val foreground = AppStateUtils.isForeground(this)
        log("foreground:$foreground")
        ActivityStackUtils.init(this)
    }
}