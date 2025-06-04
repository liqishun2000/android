package com.example.core.utils

import android.app.Activity
import android.net.Uri
import android.provider.OpenableColumns

object UriUtils {

    data class FileInfo(
        val fileName: String,
        val fileMimeType: String,
        val fileSize: Long,
    )

    fun getFileInfo(activity: Activity, uri: Uri): FileInfo {
        var fileName = ""
        var fileSize = 0L
        var mimeType = ""
        activity.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                // 获取文件名
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                fileName = cursor.getString(nameIndex)

                // 获取文件大小
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                fileSize = cursor.getLong(sizeIndex)

                // 获取MIME类型
                mimeType = activity.contentResolver.getType(uri) ?: "unknown"
            }
        }

        return FileInfo(fileName, mimeType, fileSize)
    }
}