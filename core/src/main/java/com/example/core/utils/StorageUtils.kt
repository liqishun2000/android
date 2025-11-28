package com.example.core.utils
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.TypedValue
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object StorageUtil {

    suspend fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        targetSizeDp: Int = 120,
        fileName: String? = null,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        quality: Int = 100
    ): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            var processedBitmap = bitmap

            // 如果目标尺寸小于原图尺寸，进行缩放处理
            val targetSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                targetSizeDp.toFloat(),
                context.resources.displayMetrics
            ).toInt()

            processedBitmap = scaleBitmapToSize(processedBitmap, targetSizePx)

            val finalFileName = fileName ?: "temp_${System.currentTimeMillis()}.${getFileExtension(format)}"
            val filesDir = context.filesDir
            val imageFile = File(filesDir, finalFileName)

            FileOutputStream(imageFile).use { fos ->
                processedBitmap.compress(format, quality, fos)
            }

            imageFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    /**
     * 从URI读取图片，处理方向并保存到私有目录
     */
    suspend fun saveImageFromUri(
        context: Context,
        imageUri: Uri,
        targetSizeDp: Int = 120,
        quality: Int = 100
    ): String?  = withContext(Dispatchers.IO){
        var inputStream: InputStream? = null
        return@withContext try {
            val contentResolver: ContentResolver = context.contentResolver
            inputStream = contentResolver.openInputStream(imageUri)

            if (inputStream == null) {
                return@withContext null
            }

            val rotation = getRotationFromUri(context, imageUri)

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            // 第一次解码只获取尺寸信息
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.close()

            // 重新打开流进行实际解码
            inputStream = contentResolver.openInputStream(imageUri)
            val scaledOptions = BitmapFactory.Options().apply {
                inSampleSize = calculateInSampleSize(options, targetSizeDp, context)
            }

            var bitmap = BitmapFactory.decodeStream(inputStream, null, scaledOptions)
            inputStream?.close()

            if (bitmap == null) {
                return@withContext null
            }

            bitmap = rotateBitmap(bitmap, rotation)

            val targetSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                targetSizeDp.toFloat(),
                context.resources.displayMetrics
            ).toInt()

            bitmap = scaleBitmapToSize(bitmap, targetSizePx)

            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val filesDir = context.filesDir
            val imageFile = File(filesDir, fileName)

            FileOutputStream(imageFile).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
            }

            bitmap.recycle()

            imageFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //region 私有方法
    /**
     * 从URI获取图片的旋转角度
     */
    private fun getRotationFromUri(context: Context, uri: Uri): Int {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val exif = ExifInterface(inputStream)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                inputStream.close()

                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            } else {
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * 计算采样率，避免加载过大图片到内存
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        targetSizeDp: Int,
        context: Context
    ): Int {
        val targetSizePx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            targetSizeDp.toFloat(),
            context.resources.displayMetrics
        ).toInt()

        val (width, height) = options.run { outWidth to outHeight }
        var inSampleSize = 1

        if (height > targetSizePx || width > targetSizePx) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= targetSizePx &&
                halfWidth / inSampleSize >= targetSizePx) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    /**
     * 旋转Bitmap
     */
    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        if (degrees == 0) return bitmap

        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())

        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )
    }

    /**
     * 缩放Bitmap到目标尺寸
     */
    private fun scaleBitmapToSize(original: Bitmap, maxSize: Int): Bitmap {
        val width = original.width
        val height = original.height

        if (width <= maxSize && height <= maxSize) {
            return original
        }

        val scaleRatio = if (width > height) {
            maxSize.toFloat() / width
        } else {
            maxSize.toFloat() / height
        }

        val newWidth = (width * scaleRatio).toInt()
        val newHeight = (height * scaleRatio).toInt()

        return original.scale(newWidth, newHeight)
    }

    /**
     * 根据压缩格式获取文件扩展名
     */
    private fun getFileExtension(format: Bitmap.CompressFormat): String {
        return when (format) {
            Bitmap.CompressFormat.JPEG -> "jpg"
            Bitmap.CompressFormat.PNG -> "png"
            Bitmap.CompressFormat.WEBP -> "webp"
            else -> "jpg"
        }
    }
    //endregion
}