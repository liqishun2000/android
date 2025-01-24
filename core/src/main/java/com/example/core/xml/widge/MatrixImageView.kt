package com.example.core.xml.widge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.appcompat.widget.AppCompatImageView
import com.example.core.ktx.log

class MatrixImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {


    /** 缩放手势 */
    private val scaleGestureListener:ScaleGestureDetector
    /** 手势监听 */
    private val gestureDetector:GestureDetector

    /** 控制矩阵 */
    private val controlMatrix = Matrix()
    /** 原始矩阵 用于还原图片 */
    private val originMatrix = Matrix()

    init {
        scaleType = ScaleType.MATRIX

        scaleGestureListener = ScaleGestureDetector(context,object :SimpleOnScaleGestureListener(){
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                startScale(detector)
                return true
            }
        })

        gestureDetector = GestureDetector(context,object : SimpleOnGestureListener(){
            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                startScroll(-distanceX,-distanceY)
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                controlMatrix.reset()
                controlMatrix.postConcat(originMatrix)
                imageMatrix = controlMatrix
                return true
            }
        })

        //初始化
        post {
            if(drawable == null) return@post

            //drawable固有宽高
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight

            //移动至中心点
            val translationX = width/2f-drawableWidth/2f
            val translationY = height/2f-drawableHeight/2f
            controlMatrix.postTranslate(translationX,translationY)

            val scale = if(drawableWidth>=width && drawableHeight<= height){
                //超宽 以宽缩放
                width*1f/drawableWidth
            }else if(drawableWidth<= width && drawableHeight>= height){
                //超高 以高缩放
                height*1f/drawableHeight
            }else{
                //都超取最小值 或 都不超取最小放大比
                (width*1f/drawableWidth).coerceAtMost( height*1f/drawableHeight)
            }

            log("width:$width height:$height translationX:$translationX translationY:$translationY scale:$scale")
            //缩放
            controlMatrix.postScale(scale,scale,width/2f,height/2f)

            //原始矩阵初始化
            originMatrix.postConcat(controlMatrix)

            imageMatrix = controlMatrix
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return scaleGestureListener.onTouchEvent(event) or gestureDetector.onTouchEvent(event)
    }

    private fun startScale(detector: ScaleGestureDetector){
        val scale = detector.scaleFactor
        controlMatrix.postScale(scale,scale,detector.focusX,detector.focusY)
        imageMatrix = controlMatrix
    }

    private fun startScroll(distanceX:Float,distanceY:Float){
        controlMatrix.postTranslate(distanceX,distanceY)
        imageMatrix = controlMatrix
    }
}
