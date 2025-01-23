package com.example.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipboardUtils {

    /** 复制内容到剪贴板 */
    fun copyToClipboard(context: Context, string: String) {
        val clipboard: ClipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clip = ClipData.newPlainText(string.take(10), string)

        clipboard.setPrimaryClip(clip)
    }
}