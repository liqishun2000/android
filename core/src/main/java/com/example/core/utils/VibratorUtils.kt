package com.example.core.utils

import android.content.Context
import android.os.Vibrator

object VibratorUtils {

    fun vibrate(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(100)
    }
}