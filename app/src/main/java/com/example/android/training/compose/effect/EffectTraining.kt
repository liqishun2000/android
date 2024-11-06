package com.example.android.training.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.example.core.utils.commonLog


@Composable
fun EffectTraining(
    key:Int,
    modifier: Modifier = Modifier,
) {

    //每次重组都会运行 A SideEffect runs after every recomposition。重组代码运行完才运行
    SideEffect {
        commonLog("SideEffect")
    }

    //启动一个协程作用域 main线程
    LaunchedEffect(Unit) {
        commonLog("LaunchedEffect ${Thread.currentThread().name}")
    }

    //被移除 或者 key改变，都会调用 onDispose
    DisposableEffect(key) {
        commonLog("DisposableEffect")
        onDispose {
            commonLog("DisposableEffect onDispose")
        }
    }
}