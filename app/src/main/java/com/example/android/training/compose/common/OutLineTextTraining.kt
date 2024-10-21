package com.example.android.training.compose.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun Preview() {
    OutLineText()
}

@Composable
private fun OutLineText(modifier: Modifier = Modifier) {

    val density = LocalDensity.current

    Button(onClick = {  }) {
        Box{
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
}