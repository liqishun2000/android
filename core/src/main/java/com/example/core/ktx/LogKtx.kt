package com.example.core.ktx

import android.util.Log

fun log(msg: String) = log("...",msg)

fun logActivityStack(msg:String) = log("activity_stack",msg)

private fun log(tag:String,msg:String){
    if (msg.length <= 1000) {
        Log.d(tag, msg)
    } else {
        var currentIndex = 0
        val list: MutableList<String> = mutableListOf()
        while (currentIndex < msg.length) {
            val endIndex = minOf(currentIndex + 1000, msg.length)
            val substring = msg.substring(currentIndex, endIndex)
            list.add(substring)
            currentIndex = endIndex
        }
        list.forEach {
            Log.d(tag, it)
        }
        Log.d(tag, "")
    }
}