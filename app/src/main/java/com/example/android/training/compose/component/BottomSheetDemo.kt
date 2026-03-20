package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheetDemo() {
    var showSheet by remember { mutableStateOf(false) }
    var showPartialSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("ModalBottomSheet")
        Button(onClick = { showSheet = true }) {
            Text("显示 BottomSheet")
        }
        Button(onClick = { showPartialSheet = true }) {
            Text("部分展开 BottomSheet")
        }
    }

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false }) {
            BottomSheetContent()
        }
    }

    if (showPartialSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
        val scope = rememberCoroutineScope()

        ModalBottomSheet(
            onDismissRequest = { showPartialSheet = false },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("部分展开的 BottomSheet", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("可以通过拖动来完全展开或收起")
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = {
                        scope.launch { sheetState.expand() }
                    }) {
                        Text("完全展开")
                    }
                    Button(onClick = { showPartialSheet = false }) {
                        Text("关闭")
                    }
                }
                Spacer(Modifier.height(200.dp))
                Text("底部内容区域", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun BottomSheetContent() {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(
            "选择操作",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        HorizontalDivider()
        ListItem(
            headlineContent = { Text("收藏") },
            supportingContent = { Text("添加到收藏夹") },
            leadingContent = { Icon(Icons.Default.Favorite, contentDescription = null) }
        )
        ListItem(
            headlineContent = { Text("分享") },
            supportingContent = { Text("分享给好友") },
            leadingContent = { Icon(Icons.Default.Share, contentDescription = null) }
        )
        ListItem(
            headlineContent = { Text("评分") },
            supportingContent = { Text("给个好评吧") },
            leadingContent = { Icon(Icons.Default.Star, contentDescription = null) }
        )
    }
}
