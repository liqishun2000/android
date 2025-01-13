package com.al.dailybible.ui.common.refresh

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

@SuppressLint("ModifierParameter")
@Composable
fun RefreshContainer(
    state: RefreshState,
    isRefreshing: Boolean?,
    modifier: Modifier = Modifier,
    indicator: @Composable (RefreshState) -> Unit = { DefaultRefreshIndicator(state = state) },
) {
    check(state is RefreshStateImpl)

    var containerSize by remember { mutableStateOf<IntSize?>(null) }

    state.setContainerSize(containerSize)

    if (isRefreshing != null) {
        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                state.showRefresh()
            } else {
                state.hideRefresh()
            }
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged {
                containerSize = it
            }
            .graphicsLayer {
                when (state.refreshDirection) {
                    RefreshDirection.Top -> translationY = state.offset - size.height
                    RefreshDirection.Bottom -> translationY = state.offset + size.height
                    RefreshDirection.Left -> translationX = state.offset - size.width
                    RefreshDirection.Right -> translationX = state.offset + size.width
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        indicator(state)
    }
}

/**
 * 顶部刷新
 */
@Composable
fun rememberRefreshStateTop(
    enabled: () -> Boolean = { true },
    onRefresh: () -> Unit,
): RefreshState {
    return rememberRefreshState(
        refreshDirection = RefreshDirection.Top,
        enabled = enabled,
        onRefresh = onRefresh,
    )
}

/**
 * 底部刷新
 */
@Composable
fun rememberRefreshStateBottom(
    enabled: () -> Boolean = { true },
    onRefresh: () -> Unit,
): RefreshState {
    return rememberRefreshState(
        refreshDirection = RefreshDirection.Bottom,
        enabled = enabled,
        onRefresh = onRefresh,
    )
}

/**
 * 左侧刷新
 */
@Composable
fun rememberRefreshStateLeft(
    enabled: () -> Boolean = { true },
    onRefresh: () -> Unit,
): RefreshState {
    return rememberRefreshState(
        refreshDirection = RefreshDirection.Left,
        enabled = enabled,
        onRefresh = onRefresh,
    )
}

/**
 * 右侧刷新
 */
@Composable
fun rememberRefreshStateRight(
    enabled: () -> Boolean = { true },
    onRefresh: () -> Unit,
): RefreshState {
    return rememberRefreshState(
        refreshDirection = RefreshDirection.Right,
        enabled = enabled,
        onRefresh = onRefresh,
    )
}

@Composable
private fun rememberRefreshState(
    refreshDirection: RefreshDirection,
    enabled: () -> Boolean = { true },
    onRefresh: () -> Unit,
): RefreshState {
    val coroutineScope = rememberCoroutineScope()
    return remember(refreshDirection, enabled, coroutineScope) {
        RefreshStateImpl(
            coroutineScope = coroutineScope,
            refreshDirection = refreshDirection,
            enabled = enabled,
        )
    }.apply {
        setRefreshCallback(onRefresh)
    }
}
