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

    fun getClipboardContent(context: Context):CharSequence? {
        // 获取ClipboardManager实例
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // 获取剪贴板内容
        val clipData: ClipData? = clipboard.primaryClip

        return if (clipData != null && clipData.itemCount > 0) {
            // 获取剪贴板的第一项并转换为文本
            clipData.getItemAt(0).text
        } else {
            null
        }
    }
}