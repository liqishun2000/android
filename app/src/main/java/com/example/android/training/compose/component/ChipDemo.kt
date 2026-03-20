package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewChipDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("AssistChip (辅助标签)")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(
                onClick = {},
                label = { Text("日程") },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.then(AssistChipDefaults.IconSize.let { Modifier })) }
            )
            AssistChip(
                onClick = {},
                label = { Text("搜索") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            ElevatedAssistChip(
                onClick = {},
                label = { Text("Elevated Chip") }
            )
        }

        SectionTitle("FilterChip (筛选标签)")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val filters = listOf("全部", "进行中", "已完成", "已取消")
            filters.forEach { filter ->
                var selected by remember { mutableStateOf(filter == "全部") }
                FilterChip(
                    selected = selected,
                    onClick = { selected = !selected },
                    label = { Text(filter) },
                    leadingIcon = if (selected) {
                        { Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.then(FilterChipDefaults.IconSize.let { Modifier })) }
                    } else null
                )
            }
        }

        SectionTitle("InputChip (输入标签)")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            var tags by remember { mutableStateOf(listOf("Android", "Kotlin", "Compose")) }
            tags.forEach { tag ->
                InputChip(
                    selected = false,
                    onClick = {},
                    label = { Text(tag) },
                    avatar = { Icon(Icons.Default.Face, contentDescription = null) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "删除",
                            modifier = Modifier.then(InputChipDefaults.IconSize.let { Modifier })
                        )
                    }
                )
            }
        }

        SectionTitle("SuggestionChip (建议标签)")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val suggestions = listOf("Jetpack Compose", "Material 3", "Kotlin", "协程", "Flow")
            suggestions.forEach { suggestion ->
                SuggestionChip(
                    onClick = {},
                    label = { Text(suggestion) }
                )
            }
        }
    }
}
