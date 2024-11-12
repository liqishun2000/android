package com.example.core.compose.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun Preview() {
    DashedVerticalLine(
        color = Color.Red,
        width = 2.dp,
        modifier = Modifier.height(100.dp)
    )
}

@Composable
fun DashedVerticalLine(
    color:Color,
    width:Dp,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.width(width = width)
    ) {
        val path = Path().apply {
            // 移动到起始点
            moveTo(0f, 0f)
            // 画一条从顶部到底部的直线
            lineTo(0f, size.height)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = width.toPx(), // 线条宽度
                // 定义虚线的模式（例如，每个线段长10dp，间隔5dp）
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()), 0f)
            )
        )
    }
}

@Composable
fun DashedHorizontalLine(
    color:Color,
    width:Dp,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.height(height = width)
    ) {
        val path = Path().apply {
            val y = width.toPx()/2f
            // 移动到起始点
            moveTo(0f, y)
            // 画一条从顶部到底部的直线
            lineTo(size.width, y)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = width.toPx(), // 线条宽度
                // 定义虚线的模式（例如，每个线段长10dp，间隔5dp）
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()), 0f)
            )
        )
    }
}