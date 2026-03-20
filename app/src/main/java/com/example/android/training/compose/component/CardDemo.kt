package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun PreviewCardDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("Card (填充卡片)")
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("标题", style = MaterialTheme.typography.titleMedium)
                Text("这是一个基本的 Card 组件，使用填充背景色。", style = MaterialTheme.typography.bodyMedium)
            }
        }

        SectionTitle("Card (可点击)")
        Card(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("可点击卡片", style = MaterialTheme.typography.titleMedium)
                Text("点击此卡片会触发 onClick 回调", style = MaterialTheme.typography.bodyMedium)
            }
        }

        SectionTitle("ElevatedCard (凸起卡片)")
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("ElevatedCard", style = MaterialTheme.typography.titleMedium)
                Text("带阴影效果的凸起卡片，elevation = 6.dp", style = MaterialTheme.typography.bodyMedium)
            }
        }

        SectionTitle("OutlinedCard (描边卡片)")
        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("OutlinedCard", style = MaterialTheme.typography.titleMedium)
                Text("带边框的描边卡片样式", style = MaterialTheme.typography.bodyMedium)
            }
        }

        SectionTitle("自定义颜色卡片")
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9),
                contentColor = Color(0xFF2E7D32)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("成功提示", style = MaterialTheme.typography.titleMedium)
                Text("自定义背景色和内容色的卡片", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
