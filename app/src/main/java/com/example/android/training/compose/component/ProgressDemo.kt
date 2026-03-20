package com.example.android.training.compose.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun PreviewProgressDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("LinearProgressIndicator (线性进度)")

        Text("不确定进度 (indeterminate)")
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        var progress by remember { mutableFloatStateOf(0.3f) }
        val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")

        Text("确定进度: ${(progress * 100).toInt()}%")
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxWidth(),
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { progress = (progress - 0.1f).coerceAtLeast(0f) }) {
                Text("-10%")
            }
            Button(onClick = { progress = (progress + 0.1f).coerceAtMost(1f) }) {
                Text("+10%")
            }
        }

        Text("自定义颜色和形状")
        LinearProgressIndicator(
            progress = { 0.7f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color(0xFF4CAF50),
            trackColor = Color(0xFFE0E0E0),
            strokeCap = StrokeCap.Round
        )

        Spacer(Modifier.height(8.dp))
        SectionTitle("CircularProgressIndicator (圆形进度)")

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(Modifier.height(4.dp))
                Text("默认", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                )
                Spacer(Modifier.height(4.dp))
                Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    strokeWidth = 6.dp,
                    color = Color(0xFFFF5722),
                    trackColor = Color(0xFFFFCCBC),
                    strokeCap = StrokeCap.Round
                )
                Spacer(Modifier.height(4.dp))
                Text("自定义", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
