package com.example.android.training.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


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

@Preview
@Composable
private fun PreviewWrapContentSize(modifier: Modifier = Modifier) {
    WrapContentSizeTraining()
}

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