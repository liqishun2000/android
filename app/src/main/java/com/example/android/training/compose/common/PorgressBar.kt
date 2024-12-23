package com.example.android.training.compose.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun PreviewGradientProgressBar() {
    HorizontalGradientProgressBar(
        progress = { 0.5f },
        brush = Brush.horizontalGradient(listOf(Color.Red, Color.Yellow)),
        progressHeight = 20.dp,
        modifier = Modifier
            .width(300.dp)
            .background(
                color = Color.White,
                shape = CircleShape
            )
    )
}

@Composable
fun HorizontalGradientProgressBar(
    progress: () -> Float,
    brush: Brush,
    progressHeight: Dp,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .clip(CircleShape)
            .requiredHeight(progressHeight)
    ) {
        drawLine(
            brush = brush,
            start = Offset(0f, progressHeight.toPx() / 2),
            end = Offset(size.width * progress(), progressHeight.toPx() / 2),
            cap = StrokeCap.Round,
            strokeWidth = progressHeight.toPx()
        )
    }
}