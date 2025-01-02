package com.example.android.training.compose.regroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.utils.TraceLogUtils
import com.example.core.utils.commonLog


@Preview
@Composable
private fun Preview() {
    SideEffect {
        commonLog("Preview")
    }

    RegroupTraining()
}

@Composable
private fun RegroupTraining(modifier: Modifier = Modifier) {


    SideEffect {
        commonLog("重组")
    }

    val stringBuilder = remember { StringBuilder() }
    var string by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var count by remember { mutableIntStateOf(0) }

        Button(onClick = {
//            stringBuilder.append("i")
//            string = stringBuilder.toString()
            count++
        }) {
            Text("点击")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .height(200.dp)
        ) {
            Text(text = count.toString())


        }


    }
}