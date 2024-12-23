package com.example.android.training.compose.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *  描边
 * */
@Preview(backgroundColor = 0xffffffff, showBackground = true)
@Composable
private fun Preview() {
    Column {
        SimpleOutLineText()

        OutlineText(
            text = "测试",
            color = Color.White,
        )

        OutlineText2(
            text = "测试",
        )
    }
}

@Composable
private fun SimpleOutLineText(modifier: Modifier = Modifier) {

    val density = LocalDensity.current
    Box {
        Text(
            text = "点击",
            color = Color.Red,
            style = LocalTextStyle.current.copy(
                drawStyle = Stroke(
                    width = with(density) { 2.dp.toPx() },
                    join = StrokeJoin.Round
                ),
                shadow = Shadow(
                    color = Color.Red,
                    blurRadius = with(density) { 1.dp.toPx() }
                )
            ),
        )
        Text(text = "点击")
    }
}


@Composable
private fun OutlineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
    /** 描边颜色 */
    outlineColor: Color = MaterialTheme.colorScheme.onSurface,
    /** 描边大小 */
    outlineWidth: Dp = 2.dp,
    /** 阴影大小 */
    shadowBlurRadius: Dp = 1.dp,
) {
    val density = LocalDensity.current
    Box(modifier = modifier) {
        Text(
            text = text,
            color = outlineColor,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style.copy(
                drawStyle = Stroke(
                    width = with(density) { outlineWidth.toPx() },
                    join = StrokeJoin.Round
                ),
                shadow = Shadow(
                    color = outlineColor,
                    blurRadius = with(density) { shadowBlurRadius.toPx() },
                )
            ),
        )
        Text(
            text = text,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
}

@Composable
fun OutlineText2(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = TextStyle(
            color = Color.Green,
            fontSize = 20.sp,
            drawStyle = Stroke(width = 6f, join = StrokeJoin.Round)
        )
    )
}
