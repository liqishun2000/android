package com.example.android.util

import android.util.Log
import android.view.View
import android.view.ViewGroup

object ViewUtils {

    //region 遍历viewTree
    @JvmStatic
    private fun traversalView(parent: View, level: Int) {
        if (parent is ViewGroup) {
            outPut(parent, level)
            for (i in 0 until parent.childCount) {
                val childAt = parent.getChildAt(i)
                traversalView(childAt, level + 1)
            }
        } else {
            outPut(parent, level)
        }
    }

    private fun outPut(view: View, level: Int) {
        val stringBuilder = StringBuilder()
        for (i in 0 until level) {
            stringBuilder.append("    ")
        }
        try {
            Log.d(
                "...",
                stringBuilder.toString() + "level: $level name: ${view::class.java.simpleName} id:${
                    view.context!!.resources.getResourceEntryName(
                        view.id
                    )
                }"
            )
        } catch (e: Exception) {
            Log.d(
                "...",
                stringBuilder.toString() + "level: $level name: ${view::class.java.simpleName}"
            )
        }

    }
    //endregion

}