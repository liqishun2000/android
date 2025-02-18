package com.example.android.training.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.OutputStream

//View创建
fun createBitmapFromView(view: View): Bitmap {
    return Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888).apply {
        Canvas(this).apply {
            view.draw(this)
        }
    }
}

//工厂创建
fun bitmapFactory() {
//    BitmapFactory.decodeResource()
//    BitmapFactory.decodeFile()
//    BitmapFactory.decodeByteArray()
}

//常用方法
fun commonFun() {
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

    //复制
    val copyBitmap = bitmap.copy(
        Bitmap.Config.ARGB_8888,
        true //是否可以修改
    )

    //缩放
    val scaleBitmap = Bitmap.createScaledBitmap(
        bitmap,
        200,
        200,
        true //平滑缩放（提高质量）
    )

    //压缩图片到流
    bitmap.compress(
        Bitmap.CompressFormat.PNG,
        100,
        ByteArrayOutputStream()
    )

    // 获取像素数据
    val pixels = IntArray(bitmap.width * bitmap.height)
    bitmap.getPixels(
        pixels,      // 接收数组
        0,     // 起始偏移
        bitmap.width,// 行跨度
        0, 0,  // 获取起始坐标
        bitmap.width,// 获取宽度
        bitmap.height// 获取高度
    )

}
