package com.example.core.compose.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import java.util.concurrent.atomic.AtomicReference

/**
 * Tab容器
 */
@Composable
fun TabContainer(
    modifier: Modifier = Modifier,
    selectedKey: Any,
    apply: TabContainerScope.() -> Unit,
) {
    val container = remember {
        TabContainerImpl()
    }.apply {
        config(apply)
    }

    Box(modifier = modifier) {
        container.Content(selectedKey)
    }
}

interface TabContainerScope {
    fun tab(
        key: Any,
        display: TabDisplay? = null,
        content: @Composable () -> Unit,
    )
}

/**
 * 当选中状态变化时，如何显示隐藏Tab，默认的实现[DefaultDisplay]
 */
typealias TabDisplay = @Composable (content: @Composable () -> Unit, selected: Boolean) -> Unit

private val DefaultDisplay: TabDisplay = { content: @Composable () -> Unit, selected: Boolean ->
    Box(
        modifier = Modifier.graphicsLayer {
            val scale = if (selected) 1f else 0f
            scaleX = scale
            scaleY = scale
        }
    ) {
        content()
    }
}

private class TabContainerImpl : TabContainerScope {
    private val _store: MutableMap<Any, TabInfo> = mutableMapOf()
    private val _activeTabs: MutableMap<Any, TabState> = mutableStateMapOf()

    private var _configState = AtomicReference(ConfigState.None)

    fun config(apply: TabContainerScope.() -> Unit) {
        _configState.compareAndSet(ConfigState.None, ConfigState.Config)
        apply()
        _configState.compareAndSet(ConfigState.Config, ConfigState.ConfigFinish)
    }

    override fun tab(
        key: Any,
        display: TabDisplay?,
        content: @Composable () -> Unit,
    ) {
        if (_configState.get() == ConfigState.Config) {
            val info = _store[key]
            if (info == null) {
                _store[key] = TabInfo(display = display, content = content)
            } else {
                info.display = display
                info.content = content
            }
        }
    }

    private fun checkConfig() {
        if (_configState.compareAndSet(ConfigState.ConfigFinish, ConfigState.None)) {
            _activeTabs.forEach { active ->
                val info = checkNotNull(_store[active.key])
                active.value.apply {
                    this.display.value = info.display
                    this.content.value = info.content
                }
            }
        }
    }

    private fun activeTab(key: Any) {
        if (_activeTabs[key] == null) {
            val info = checkNotNull(_store[key]) { "Key $key was not found." }
            _activeTabs[key] = TabState(
                display = mutableStateOf(info.display),
                content = mutableStateOf(info.content),
            )
        }
    }

    @Composable
    fun Content(selectedKey: Any) {
        SideEffect {
            checkConfig()
        }

        LaunchedEffect(selectedKey) {
            activeTab(selectedKey)
        }

        for ((key, state) in _activeTabs) {
            key(key) {
                val display = state.display.value ?: DefaultDisplay
                display(state.content.value, key == selectedKey)
            }
        }
    }

    enum class ConfigState {
        None,
        Config,
        ConfigFinish,
    }
}

private class TabInfo(
    var display: TabDisplay?,
    var content: @Composable () -> Unit,
)

private class TabState(
    val display: MutableState<TabDisplay?>,
    val content: MutableState<@Composable () -> Unit>,
)