package com.example.core.xml.widge

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView

/** 固定宽高，textSize自动缩小 */
class AutoResizeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var minTextSizePx = 1f // 最小字体大小
    private val defaultTextSizePx = textSize

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        adjustTextSize()
    }

    private fun adjustTextSize() {
        val targetText = text?.toString() ?: return
        if (width == 0 || height == 0) return

        val targetWidth = width - paddingLeft - paddingRight
        val targetHeight = height - paddingTop - paddingBottom

        // 获取TextView参数
        val params = TextViewParams(
            lineSpacingExtra = lineSpacingExtra,
            lineSpacingMultiplier = lineSpacingMultiplier,
            includeFontPadding = includeFontPadding,
            maxLines = maxLines,
            alignment = getLayoutAlignment()
        )

        var currentSize = defaultTextSizePx
        var suitableSize = minTextSizePx
        var found = false

        while (currentSize >= minTextSizePx) {
            if (isTextFits(targetText, currentSize, targetWidth, targetHeight, params)) {
                suitableSize = currentSize
                found = true
                break
            }
            currentSize -= 1
        }

        // 设置最终大小
        setTextSize(TypedValue.COMPLEX_UNIT_PX, if (found) suitableSize else minTextSizePx)
    }

    private fun isTextFits(
        text: String,
        textSize: Float,
        targetWidth: Int,
        targetHeight: Int,
        params: TextViewParams
    ): Boolean {
        val paint = TextPaint(paint).apply { this.textSize = textSize }
        val alignment = params.alignment

        val layout = StaticLayout.Builder.obtain(text, 0, text.length, paint, targetWidth)
            .setAlignment(alignment)
            .setLineSpacing(params.lineSpacingExtra, params.lineSpacingMultiplier)
            .setIncludePad(params.includeFontPadding)
            .setMaxLines(if (params.maxLines > 0) params.maxLines else Int.MAX_VALUE)
            .build()

        return layout.height <= targetHeight && (params.maxLines <= 0 || layout.lineCount <= params.maxLines)
    }

    private fun getLayoutAlignment(): Layout.Alignment {
        return when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.CENTER_HORIZONTAL -> Layout.Alignment.ALIGN_CENTER
            else -> Layout.Alignment.ALIGN_NORMAL
        }
    }

    private data class TextViewParams(
        val lineSpacingExtra: Float,
        val lineSpacingMultiplier: Float,
        val includeFontPadding: Boolean,
        val maxLines: Int,
        val alignment: Layout.Alignment
    )
}
