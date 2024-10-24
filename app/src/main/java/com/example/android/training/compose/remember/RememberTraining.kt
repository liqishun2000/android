package com.example.android.training.compose.remember

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.utils.commonLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Preview
@Composable
private fun Preview() {
    RememberUpdatedStateTraining()
}

//region rememberUpdatedState 为了保持最新状态，在异步处理耗时任务时，需要把State直接传出去，通过委托传值在耗时任务时值可能已经改变。
@Composable
private fun RememberUpdatedStateTraining(modifier: Modifier = Modifier) {

    var intState by remember {
        mutableIntStateOf(0)
    }

    val updatedState = rememberUpdatedState(intState)
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
            while (true){
                delayTask(intState,updatedState)
            }
        }

    }

    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Hello World",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    intState++
                    commonLog("click intState: $intState")
                }
        )
    }
}

private suspend fun delayTask(value:Int,updatedValue:State<Int>){
    delay(2000)
    commonLog("value:$value updatedValue:$updatedValue")
}
//endregion
