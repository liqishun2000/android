package com.example.core.utils

import android.util.Log

fun commonLog(msg: String) {
    Log.d("...", msg)
}

object TraceLogUtils {
    private const val TAG = "..."

    fun log(msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        //本身
        val stackTraceElement = stackTrace[3]
        val `fun` = stackTraceElement.methodName
        val className =
            stackTraceElement.fileName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0]

        //上一层
        var stackTraceElementParent: StackTraceElement
        var funParent = ""
        var classNameParent = ""
        for (i in 4 until stackTrace.size) {
            stackTraceElementParent = stackTrace[i]
            if (stackTraceElementParent.methodName.contains("$")) {
                continue
            }
            funParent = stackTraceElementParent.methodName
            classNameParent = stackTraceElementParent.fileName.split(".")[0]
            break
        }


        Log.d(TAG, "$classNameParent.$funParent: $className -> $`fun`: $msg")
    }

    fun progressLog(tag: String?, msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        //本身
        val stackTraceElement = stackTrace[3]
        val `fun` = stackTraceElement.methodName
        val className =
            stackTraceElement.fileName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0]

        //上一层
        var stackTraceElementParent: StackTraceElement
        var funParent = ""
        var classNameParent = ""
        for (i in 4 until stackTrace.size) {
            stackTraceElementParent = stackTrace[i]
            if (stackTraceElementParent.methodName.contains("$")) {
                continue
            }
            funParent = stackTraceElementParent.methodName
            classNameParent = stackTraceElementParent.fileName.split(".")[0]
            break
        }

        Log.d(tag, "$classNameParent.$funParent: $className -> $`fun`: $msg")
    }
}
