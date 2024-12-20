package com.example.android.training.compose.lazy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.LocalPinnableContainer
import androidx.compose.ui.layout.PinnableContainer

@Composable
fun pin(): PinnableContainer.PinnedHandle? {
    val container = LocalPinnableContainer.current
    val pinnedHandle = remember(container) { container?.pin() }
    if (pinnedHandle != null) {
        DisposableEffect(pinnedHandle) {
            onDispose {
                pinnedHandle.release()
            }
        }
    }
    return pinnedHandle
}