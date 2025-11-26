package com.example.android.training.compose.local

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo

@Composable
fun LocalTraining(modifier: Modifier = Modifier) {
    /** 新版屏幕宽度获取 */
    val screenWidth = LocalWindowInfo.current.containerSize.width

}