package com.example.android.training.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.ktx.log


@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {
    BasicTraining()
}

@Composable
private fun BasicTraining(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.Blue)
            .requiredSize(30.dp)
    )
}






//region .drawBehind .drawWithContent
@Preview
@Composable
private fun PreviewDraw(modifier: Modifier = Modifier) {
    DrawTraining()
}

@Composable
private fun DrawTraining(modifier: Modifier = Modifier) {
    //graphicsLayer{}是在布局阶段，在绘制阶段之前
    //drawBehind在drawWithContent之前 按顺序运行
    //drawWithContent如果不调用drawContent()方法，那么后面的draw不会调用，Box子项也不会绘制
    //drawContent() 开始绘制 之后的draw方法和内容布局,绘制完成后drawContent()结束

    Box(
        modifier = Modifier
            .size(50.dp)
            .drawBehind {
                log("drawBehind before")
            }
            .drawWithContent {
                log("drawWithContent before")
                drawContent()
                log("drawWithContent after")
            }
            .drawBehind {
                log("drawBehind after")
            }
            .graphicsLayer {
                log("graphics")
            }
    ){
        Text("text", modifier = Modifier.drawBehind {
            log("draw child")
        })
    }
}
//endregion

//region .requiredSize
@Preview
@Composable
private fun PreviewRequiredSize(modifier: Modifier = Modifier) {
    RequiredSizeTraining()
}

@Composable
private fun RequiredSizeTraining(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.Blue)
            .requiredSize(30.dp)
    )
}
//endregion

//region .wrapContentSize
@Preview
@Composable
private fun PreviewWrapContentSize(modifier: Modifier = Modifier) {
    WrapContentSizeTraining()
}


@Composable
private fun WrapContentSizeTraining(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.Blue)
            .wrapContentSize(Alignment.Center)
            .size(20.dp)
            .background(Color.Red)
    )
}
//endregion