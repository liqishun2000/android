package com.example.core.ktx

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.TextViewCompat
import kotlin.math.ceil

fun View.setMargin(
    startDp: Float = 0f,
    endDp: Float = 0f,
    topDp: Float = 0f,
    bottomDp: Float = 0f
) {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams

    val context = this.context
    params.setMargins(
        startDp.dpToPx(context),
        topDp.dpToPx(context),
        endDp.dpToPx(context),
        bottomDp.dpToPx(context)
    )

    this.layoutParams = params
}

//region 显示隐藏动效
fun View.showView() {
    this.visibility = View.VISIBLE
    // 从底部滑出（初始位置在屏幕外）
    this.translationY = this.height.toFloat()
    this.animate()
        .translationY(0f)
        .setDuration(300)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.hideView(end: () -> Unit = {}) {
    this.animate()
        .translationY(this.height.toFloat())
        .setDuration(300)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction {
            this.visibility = View.GONE
            // 重置位置以便下次显示
            this.translationY = 0f

            end.invoke()
        }
        .start()
}

fun View.showViewAlpha() {
    this.visibility = View.VISIBLE
    this.alpha = 0f
    this.animate()
        .alpha(1f)
        .setDuration(300)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.hideViewAlpha() {
    this.animate()
        .alpha(0f)
        .setDuration(300)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction {
            this.visibility = View.GONE
            this.alpha = 1f
        }
        .start()
}
//endregion

//region 状态栏高度
fun View.getStatusBarHeight(): Int {
    val windowInsets = ViewCompat.getRootWindowInsets(this)
    windowInsets?.let {
        val insets = it.getInsets(WindowInsetsCompat.Type.statusBars())
        return insets.top
    }
    return 0
}
//endregion

//region 导航栏高度
fun View.getNavigationBarHeight(): Int {
    val windowInsets = ViewCompat.getRootWindowInsets(this)
    windowInsets?.let {
        val insets = it.getInsets(WindowInsetsCompat.Type.navigationBars())
        return insets.bottom
    }
    return 0
}
//endregion

//region TextView

// 文字完全显示
fun TextView.fullDisplay(): Boolean {
    val height = this.height
    val paint = this.paint
    val textWidth = paint.measureText(this.text.toString())
    val textHeight: Int =
        ceil(textWidth / this.width).toInt() * this.lineHeight
    return textHeight <= height
}


// 自动缩放TextView
fun createAutoSizeTextView(context: Context): TextView {
    return TextView(context).apply {
        ellipsize = null // 禁用省略号
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        // 使用AndroidX兼容库
        TextViewCompat.setAutoSizeTextTypeWithDefaults(
            this,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
        )
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
            this,
            1,
            100,
            2,
            TypedValue.COMPLEX_UNIT_PX
        )
    }
}
//endregion
