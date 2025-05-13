package com.example.android.training.xml

import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import androidx.core.widget.TextViewCompat

/**
 * TextView自带字体缩放功能
 * xml设置属性:
 *         android:autoSizeMinTextSize="1sp"
 *         android:autoSizeStepGranularity="1px"
 *         android:autoSizeTextType="uniform"
 * 动态设置:
 **/
private fun createAutoSizeTextView(context: Context): TextView {
    return TextView(context).apply {
        ellipsize = null
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
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