package com.example.core.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.core.ktx.toastMsg
import java.io.File
import java.io.FileOutputStream

object IntentUtils {


    //region share info
    /**
     * @param data must String type, otherwise display error
     * */
    fun openShare(context: Context, data:String){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain") // 设置分享的内容类型
        shareIntent.putExtra(Intent.EXTRA_TEXT, data) // 将内容放入Intent
        context.startActivity(Intent.createChooser(shareIntent, "")) // 启动分享选择器
    }

    fun shareBitmaps(context: Context, bitmaps: List<Bitmap>) {
        if (bitmaps.isEmpty()) return

        val uris = ArrayList<Uri>()
        bitmaps.forEach { bitmap ->
            uris.add(bitmapToUri(context, bitmap))
        }

        val intent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            type = "image/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, ""))
    }

    private fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "share_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // 需配置 FileProvider
            file
        )
    }

//    <provider
//    android:name="androidx.core.content.FileProvider"
//    android:authorities="${applicationId}.fileprovider"
//    android:exported="false"
//    android:grantUriPermissions="true">
//    <meta-data
//    android:name="android.support.FILE_PROVIDER_PATHS"
//    android:resource="@xml/file_paths" />
//    </provider>
    //endregion

    private fun openFileWithOtherApp(context: Context, uri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, context.contentResolver.getType(uri))
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "open file"))
        } catch (e: ActivityNotFoundException) {
            toastMsg("No program was found that could open this file.", context)
        }
    }

    private fun openFileWithOtherApp(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        openFileWithOtherApp(context,uri)
    }
}