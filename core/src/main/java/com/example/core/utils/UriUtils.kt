package com.example.core.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

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

//    fun getUriFromFile(filePath: String,context: Context): android.net.Uri? {
//        return try {
//            val file = File(filePath)
//            // 确保文件存在
//            if (!file.exists()) {
//                return null
//            }
//            FileProvider.getUriForFile(
//                context,
//                "${context.packageName}.provider",  // 与 manifest 中配置的 authority 一致
//                file
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
}