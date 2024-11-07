package com.example.core.compose.ktx

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView

/**
 * 从[Context]中查找[Activity]，并调用[block]
 */
inline fun Context.findActivity(
    block: (Activity) -> Unit,
) {
    findActivityOrNull()?.let(block)
}

/**
 * 从[Context]中查找[Activity]，如果找不到的话返回null
 */
tailrec fun Context.findActivityOrNull(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivityOrNull()
        else -> null
    }

@Composable
fun getActivityWindow(): Window? = LocalView.current.context.getActivityWindow()

private tailrec fun Context.getActivityWindow(): Window? = when (this) {
    is Activity -> window
    is ContextWrapper -> baseContext.getActivityWindow()
    else -> null
}