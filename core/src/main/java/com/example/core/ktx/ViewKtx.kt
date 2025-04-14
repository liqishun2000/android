package com.example.core.ktx

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

fun View.hideView(end:()->Unit = {}) {
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

fun View.showViewAlpha(){
    this.visibility = View.VISIBLE
    this.alpha = 0f
    this.animate()
        .alpha(1f)
        .setDuration(300)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.hideViewAlpha(){
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

fun View.setMargin(startDp: Float = 0f, endDp: Float = 0f, topDp: Float = 0f, bottomDp: Float = 0f) {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams

    val context = this.context
    params.setMargins(startDp.dpToPx(context), topDp.dpToPx(context), endDp.dpToPx(context), bottomDp.dpToPx(context))

    this.layoutParams = params
}

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
fun View.getNavigationBarHeight():Int{
    val windowInsets = ViewCompat.getRootWindowInsets(this)
    windowInsets?.let {
        val insets = it.getInsets(WindowInsetsCompat.Type.navigationBars())
        return insets.bottom
    }
    return 0
}
//endregion
