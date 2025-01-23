package com.example.android.training.compose.base

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ktx.log


@Preview
@Composable
private fun Preview() {
    BaseTraining()
}

@Composable
fun BaseTraining(
    modifier: Modifier = Modifier,
) {

    with(LocalDensity.current){
        val a = 1
        a.toDp()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Button(
            onClick = {

            },
        ) {
            Text(
                modifier = Modifier.width(30.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = {

                    if(it.didOverflowHeight){
                        log("didOverflowHeight")
                    }
                    if(it.hasVisualOverflow){
                        log("hasVisualOverflow")
                    }
                },
                text = "ssssssssss",
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

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawBehind {
                            log("drawBehind")
                        }
                        .drawWithContent {
                            log("drawContent")
                            drawContent()

                        }
                        .background(Color.Green)

                ) {

                    log("content")

                    clipRect(
                        left = 0f+100f,
                        top = 0f+100f,
                        right = size.width-100f,
                        bottom = size.height-100f,
                        clipOp = ClipOp.Difference
                    ) {
                        drawRect(
                            color = Color.Red
                        )

                    }

                }


            }


        }

    }

}



