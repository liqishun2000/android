package com.example.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object StorageUtils {

    /**
     * 将Bitmap图片保存到应用的内部私有目录
     * @param context 上下文对象（如Activity或Application）
     * @param bitmap 要保存的图片Bitmap对象
     * @return 保存的文件绝对路径（如果失败则返回null）
     */
    fun saveImageToPrivateStorage(context: Context, bitmap: Bitmap): String? {
        return try {
            // 生成唯一文件名，使用时间戳避免重复
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"

            // 获取内部存储私有目录（路径如：/data/data/包名/files）
            val filesDir = context.filesDir
            val imageFile = File(filesDir, fileName)

            // 使用FileOutputStream将Bitmap压缩为JPEG格式写入文件
            FileOutputStream(imageFile).use { fos ->
                bitmap.compress(CompressFormat.JPEG, 100, fos) // 质量设置为100%（无压缩）
            }

            // 返回保存文件的绝对路径，便于后续使用
            imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}