package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Preview(showBackground = true)
@Composable
private fun PreviewDialogDemo() {
    var showDialogType by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("AlertDialog")
        Button(onClick = { showDialogType = 1 }) {
            Text("基本 AlertDialog")
        }

        Button(onClick = { showDialogType = 2 }) {
            Text("带图标 AlertDialog")
        }

        SectionTitle("自定义 Dialog")
        Button(onClick = { showDialogType = 3 }) {
            Text("自定义 Dialog")
        }
    }

    when (showDialogType) {
        1 -> BasicAlertDialog(onDismiss = { showDialogType = 0 })
        2 -> IconAlertDialog(onDismiss = { showDialogType = 0 })
        3 -> CustomDialog(onDismiss = { showDialogType = 0 })
    }
}

@Composable
private fun BasicAlertDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("确认操作") },
        text = { Text("你确定要执行此操作吗？此操作无法撤销。") },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("确认") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
private fun IconAlertDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Default.Warning, contentDescription = null) },
        title = { Text("警告") },
        text = { Text("此操作可能导致数据丢失，请谨慎操作。") },
        confirmButton = {
            Button(onClick = onDismiss) { Text("我知道了") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
private fun CustomDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("自定义对话框", style = MaterialTheme.typography.titleLarge)
                Text(
                    "使用 Dialog + Card 可以完全自定义对话框的内容和样式。",
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("关闭")
                }
            }
        }
    }
}
