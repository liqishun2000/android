package com.example.android.training.compose.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
private fun Preview() {
    ClipTraining()
}

@Composable
fun ClipTraining(
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
                .height(400.dp)
                .align(Alignment.Center)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {

            //ClipOp.Difference
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                //region 差集 ClipOp.Difference
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        //绘制内容，如果调用这个，高阶函数中的内容不会运行
                        .drawWithContent {
                            //绘制高阶函数内容
                            drawContent()
                        }
                ) {

                    clipRect(
                        left = 0f + 100f,
                        top = 0f + 100f,
                        right = size.width - 100f,
                        bottom = size.height - 100f,
                        clipOp = ClipOp.Difference
                    ) {
                        drawRect(
                            color = Color.Red
                        )

                    }

                }
                //endregion

                //region 交集 ClipOp.Intersect
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {

                    //交集
                    clipRect(
                        left = 0f + 100f,
                        top = 0f + 100f,
                        right = size.width - 100f,
                        bottom = size.height - 100f,
                        clipOp = ClipOp.Intersect
                    ) {
                        drawRect(
                            color = Color.Red
                        )

                    }

                }
                //endregion
            }


        }

    }

}



