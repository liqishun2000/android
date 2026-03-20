package com.example.android.training.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun PreviewImageIconDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle("Icon (图标)")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Home, contentDescription = "首页")
            Icon(Icons.Default.Favorite, contentDescription = "收藏", tint = Color.Red)
            Icon(Icons.Default.Star, contentDescription = "星标", tint = Color(0xFFFFC107))
            Icon(Icons.Default.Settings, contentDescription = "设置", tint = Color.Gray)
            Icon(Icons.Default.Notifications, contentDescription = "通知", tint = MaterialTheme.colorScheme.primary)
        }

        SectionTitle("Icon 不同尺寸")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(24.dp))
            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(36.dp))
            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(48.dp))
        }

        SectionTitle("Filled vs Outlined 图标")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Home, contentDescription = null)
                Text("Filled", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.Home, contentDescription = null)
                Text("Outlined", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color.Red)
                Text("Filled", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
                Text("Outlined", style = MaterialTheme.typography.bodySmall)
            }
        }

        SectionTitle("Image (图片)")
        Text("使用 ImageVector:", style = MaterialTheme.typography.bodySmall)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "头像",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                colorFilter = ColorFilter.tint(Color(0xFF757575))
            )
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "用户",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFBBDEFB))
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(Color(0xFF1565C0))
            )
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "用户",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        SectionTitle("ContentScale 说明")
        Text(
            """
ContentScale.Crop - 裁剪填满
ContentScale.Fit - 等比缩放适应
ContentScale.FillBounds - 拉伸填满
ContentScale.Inside - 等比缩放不超出
ContentScale.None - 不缩放
            """.trimIndent(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
