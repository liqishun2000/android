package com.example.android.training.compose.base.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Text 新加了autoSize属性
 * */

@Preview
@Composable
private fun PreviewText() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "text", modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .background(Color.Red),
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 5.sp, maxFontSize = 15.sp),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
        )
    }
}