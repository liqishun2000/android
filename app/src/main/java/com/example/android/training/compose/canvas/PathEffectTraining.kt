package com.example.android.training.compose.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
private fun Preview() {
    PathEffectTraining()
}

@Preview
@Composable
private fun PreviewSample() {
    //冲压
    StampedPathEffectSample()
}

@Composable
fun PathEffectTraining(
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Button(
            onClick = {

            },
        ) {
            Text(
                text = "click",
                fontSize = 20.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.Center)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //dashPathEffect
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    drawLine(
                        color = Color.Red,
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
                        cap = StrokeCap.Round,
                        start = Offset(0f, 1.dp.toPx() / 2),
                        end = Offset(size.width, 1.dp.toPx() / 2)
                    )
                }

                //cornerPathEffect
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 10.dp)
                        .height(20.dp)
                ) {
                    val path = Path().apply {
                        moveTo(0f,0f)
                        lineTo(100f,0f)
                        lineTo(100f,30f)
                        close()
                    }

                    drawPath(
                        path = path,
                        color = Color.Red,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.cornerPathEffect(10f)
                        )
                    )

                }


                //stampedPathEffect
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 10.dp)
                ) {
                    val customPath = Path().apply {
                        moveTo(-10f, 0f)
                        arcTo(
                            Rect(-10f, -10f, 10f, 10f),
                            180f,
                            180f,
                            false
                        );
                    }

                    drawLine(
                        color = Color.Red,
                        strokeWidth = 20.dp.toPx(),
                        pathEffect = PathEffect.stampedPathEffect(
                            //冲压图形
                            shape = customPath,
                            //每个冲压图形后前进距离
                            advance = 20f,
                            phase = 10f,
                            style = StampedPathEffectStyle.Morph
                        ),
                        cap = StrokeCap.Round,
                        start = Offset(0f, 20.dp.toPx() / 2),
                        end = Offset(size.width, 20.dp.toPx() / 2)
                    )
                }

                //chainPathEffect
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 20.dp)
                        .height(20.dp)
                ) {
                    val customPath = Path().apply {
                        moveTo(-10f, 0f)
                        arcTo(
                            Rect(-10f, -10f, 10f, 10f),
                            180f,
                            180f,
                            false
                        );
                    }

                    drawLine(
                        color = Color.Red,
                        strokeWidth = 20.dp.toPx(),
                        pathEffect = PathEffect.chainPathEffect(
                            outer = PathEffect.stampedPathEffect(
                                //冲压图形
                                shape = customPath,
                                //每个冲压图形后前进距离
                                advance = 20f,
                                phase = 10f,
                                style = StampedPathEffectStyle.Morph
                            ),
                            inner = PathEffect.dashPathEffect(floatArrayOf(60f, 60f))
                        ),
                        cap = StrokeCap.Round,
                        start = Offset(0f, 20.dp.toPx() / 2),
                        end = Offset(size.width, 20.dp.toPx() / 2)
                    )
                }

            }


        }

    }

}

@Composable
fun StampedPathEffectSample() {
    val size = 20f
    val square = Path().apply {
        lineTo(size, 0f)
        lineTo(size, size)
        lineTo(0f, size)
        close()
    }
    Column(
        modifier = Modifier
            .height(400.dp)
            .width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val canvasModifier = Modifier
            .requiredSize(80.dp)
            .align(Alignment.CenterHorizontally)

        // StampedPathEffectStyle.Morph will modify the lines of the square to be curved to fit
        // the curvature of the circle itself. Each stamped square will be rendered as an arc
        // that is fully contained by the bounds of the circle itself
        Canvas(modifier = canvasModifier) {
            drawCircle(color = Color.Blue)
            drawCircle(
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Morph,
                        phase = 0f,
                        advance = 30f
                    )
                )
            )
        }

        Spacer(modifier = Modifier.requiredSize(10.dp))

        // StampedPathEffectStyle.Rotate will draw the square repeatedly around the circle
        // such that each stamped square is centered on the circumference of the circle and is
        // rotated along the curvature of the circle itself
        Canvas(modifier = canvasModifier) {
            drawCircle(color = Color.Blue)
            drawCircle(
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Rotate,
                        phase = 0f,
                        advance = 30f
                    )
                )
            )
        }

        Spacer(modifier = Modifier.requiredSize(10.dp))

        // StampedPathEffectStyle.Translate will draw the square repeatedly around the circle
        // with the top left of each stamped square on the circumference of the circle
        Canvas(modifier = canvasModifier) {
            drawCircle(color = Color.Blue)
            drawCircle(
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Translate,
                        phase = 0f,
                        advance = 30f
                    )
                )
            )
        }
    }
}