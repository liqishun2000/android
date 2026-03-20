package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
private fun PreviewButtonDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("Button (填充按钮)")
        Button(onClick = {}) {
            Text("Filled Button")
        }

        Button(
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("带图标的按钮")
        }

        Button(onClick = {}, enabled = false) {
            Text("Disabled Button")
        }

        SectionTitle("ElevatedButton (凸起按钮)")
        ElevatedButton(onClick = {}) {
            Text("Elevated Button")
        }

        SectionTitle("FilledTonalButton (色调按钮)")
        FilledTonalButton(onClick = {}) {
            Text("Filled Tonal Button")
        }

        SectionTitle("OutlinedButton (描边按钮)")
        OutlinedButton(onClick = {}) {
            Text("Outlined Button")
        }

        SectionTitle("TextButton (文字按钮)")
        TextButton(onClick = {}) {
            Text("Text Button")
        }

        SectionTitle("IconButton (图标按钮)")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Favorite, contentDescription = "收藏")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Share, contentDescription = "分享")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Edit, contentDescription = "编辑")
            }
        }

        SectionTitle("FloatingActionButton (浮动操作按钮)")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallFloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "添加")
            }
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "添加")
            }
            LargeFloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "添加", modifier = Modifier.size(36.dp))
            }
        }

        SectionTitle("ExtendedFloatingActionButton")
        ExtendedFloatingActionButton(
            onClick = {},
            icon = { Icon(Icons.Default.Edit, contentDescription = null) },
            text = { Text("新建") }
        )
    }
}

@Composable
internal fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier.padding(top = 8.dp)
    )
}
