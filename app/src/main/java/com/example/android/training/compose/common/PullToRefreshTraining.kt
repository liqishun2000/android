package com.example.android.training.compose.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.training.compose.common.vm.PullToRefreshVM
import com.sd.lib.compose.refresh.FRefreshContainer
import com.sd.lib.compose.refresh.rememberFRefreshStateTop

/**
 *  下拉刷新
 *
 *  用的是嵌套滚动，所以内容布局需要可以滚动
 *
 * */
@Composable
fun PullToRefreshTrainingScreen(
    vm: PullToRefreshVM = viewModel()
) {

    val state by vm.stateFlow.collectAsStateWithLifecycle()

    PullToRefreshTraining(
        onRefresh = { vm.onRefresh() },
        isRefresh = state.isRefresh,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center,
        ) {

            Text(text = "HelloWorld")
        }
    }

}

@Preview
@Composable
private fun Preview() {

}

@Composable
private fun PullToRefreshTraining(
    onRefresh: () -> Unit,
    isRefresh: Boolean?,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {

    val topRefreshState = rememberFRefreshStateTop(onRefresh = onRefresh)


    Box(
        modifier = modifier
            .nestedScroll(topRefreshState.nestedScrollConnection)
    ) {
        content()

        Box(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .clipToBounds()
        ) {
            FRefreshContainer(
                state = topRefreshState,
                isRefreshing = isRefresh,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }


    }
}