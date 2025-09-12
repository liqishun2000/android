package com.example.android.training.compose.vm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.ui.viewmodel.MainVM

@Composable
private fun TestScreen(
    modifier: Modifier = Modifier,
    vm: MainVM = viewModel() //使用viewModel()创建viewModel,声明的ViewModel不能是私有的
) {

}