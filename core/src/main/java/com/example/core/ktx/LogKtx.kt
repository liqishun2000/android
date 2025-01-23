package com.example.core.ktx

import android.util.Log

fun log(msg: String) = Log.d("...", msg)

fun logActivityStack(msg:String) = Log.d("activity_stack",msg)