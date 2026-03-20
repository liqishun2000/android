package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun PreviewBadgeSnackbarDemo() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Badge (角标)")
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BadgedBox(badge = { Badge() }) {
                    Icon(Icons.Default.Notifications, contentDescription = "通知", modifier = Modifier.size(28.dp))
                }

                BadgedBox(badge = { Badge { Text("5") } }) {
                    Icon(Icons.Default.Email, contentDescription = "邮件", modifier = Modifier.size(28.dp))
                }

                BadgedBox(badge = { Badge { Text("99+") } }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "购物车", modifier = Modifier.size(28.dp))
                }

                BadgedBox(badge = {
                    Badge(containerColor = MaterialTheme.colorScheme.error) {
                        Text("NEW")
                    }
                }) {
                    Icon(Icons.Default.Favorite, contentDescription = "收藏", modifier = Modifier.size(28.dp))
                }
            }

            SectionTitle("Snackbar (消息条)")
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("这是一条普通消息")
                }
            }) {
                Text("显示 Snackbar")
            }

            Button(onClick = {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "文件已删除",
                        actionLabel = "撤销",
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        snackbarHostState.showSnackbar("已撤销删除")
                    }
                }
            }) {
                Text("带操作的 Snackbar")
            }

            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "这条消息会显示较长时间，并且有关闭按钮",
                        withDismissAction = true,
                        duration = SnackbarDuration.Long
                    )
                }
            }) {
                Text("可关闭的 Snackbar")
            }

            SectionTitle("自定义 Snackbar")
            Snackbar(
                modifier = Modifier.fillMaxWidth(),
                action = {
                    TextButton(onClick = {}) { Text("操作") }
                },
                dismissAction = {
                    TextButton(onClick = {}) { Text("关闭") }
                }
            ) {
                Text("这是一个静态展示的 Snackbar")
            }
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}
