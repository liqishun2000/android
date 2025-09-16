package com.example.android

import android.app.Application
import com.example.core.ktx.log
import com.example.core.utils.ActivityStackUtils
import com.example.core.utils.AppStateUtils

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        val foreground = AppStateUtils.isForeground(this)
        log("foreground:$foreground")
        ActivityStackUtils.init(this)
    }
}