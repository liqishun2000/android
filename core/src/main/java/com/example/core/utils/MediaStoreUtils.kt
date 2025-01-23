package com.example.core.utils

import android.app.Activity
import android.content.ContentValues
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object MediaStoreUtils {

    fun saveBitmapToGallery(activity: Activity, bitmap: Bitmap, title: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, title)
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            contentValues.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
            ) // 保存到相册

            val uri: Uri = activity.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return

            runCatching {
                activity.contentResolver.openOutputStream(uri)?.let { outputStream->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                }
            }.onFailure { it.printStackTrace() }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val result = runCatching {
                    PermissionUtils.requestWriteStorage(activity)
                }

                if(result.isFailure){
                    result.exceptionOrNull()?.printStackTrace()
                    return@launch
                }

                // Android 9 及以下版本使用传统方式
                val fold = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                if(!fold.exists()){
                    fold.mkdirs()
                }

                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + "/" + title + ".jpg"
                val file = File(path)

                try {
                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()

                    // 通知相册更新
                    MediaScannerConnection.scanFile(activity, arrayOf(file.toString()), null, null)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
    }
}