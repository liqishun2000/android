package com.al.dailybible.ui.common.refresh

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll

/**
 *  顶部刷新
 *
 * */
@Composable
fun PullToRefresh(
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    val state = rememberRefreshStateTop { onRefresh() }

    Box(
        modifier = modifier
            .nestedScroll(state.nestedScrollConnection)
    ) {
        content()

        RefreshContainer(
            state = state,
            isRefreshing = isRefresh,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .statusBarsPadding()
                .clipToBounds()
        )
    }
}