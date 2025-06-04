package com.example.core.ktx

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

private val mHandler = Handler(Looper.getMainLooper())

fun toastMsg(msg: String, context: Context) {
    val applicationContext = context.applicationContext
    mHandler.post { Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show() }
}