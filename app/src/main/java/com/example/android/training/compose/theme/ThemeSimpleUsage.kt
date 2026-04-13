package com.example.android.training.compose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * # Theme 简单使用 — 分步学习
 *
 * 这个文件从最基础的用法开始，一步步演示如何在日常开发中使用 MaterialTheme。
 * 不涉及自定义 Theme，只聚焦「怎么用」。
 *
 * ## 核心用法只有三句话：
 * - 颜色：`MaterialTheme.colorScheme.xxx`
 * - 字体：`MaterialTheme.typography.xxx`
 * - 形状：`MaterialTheme.shapes.xxx`
 */

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun ThemeSimpleUsagePreview() {
    SampleTheme(dynamicColor = false) {
        ThemeSimpleUsageScreen()
    }
}

@Composable
fun ThemeSimpleUsageScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Step1_Colors()
            Step2_Typography()
            Step3_Shapes()
            Step4_ProfileCard()
            Step5_SettingsList()
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// ==================== Step 1: 颜色 ====================

/**
 * ## Step 1: 读取主题颜色
 *
 * 最常用的颜色：
 * - `colorScheme.primary` → 主色（按钮、强调元素）
 * - `colorScheme.onPrimary` → 主色上的文字
 * - `colorScheme.surface` → 卡片、弹窗背景
 * - `colorScheme.onSurface` → surface 上的文字（最主要的文本色）
 * - `colorScheme.background` → 页面背景
 * - `colorScheme.onSurfaceVariant` → 次要文字色
 * - `colorScheme.outline` → 分割线、边框
 */
@Composable
private fun Step1_Colors() {
    StepHeader("Step 1", "读取主题颜色")

    // 最常见的用法：给 Text 设置颜色
    Text(
        text = "我是 primary 色文字",
        color = MaterialTheme.colorScheme.primary,
    )

    Text(
        text = "我是 onSurface 色文字（主要文本色）",
        color = MaterialTheme.colorScheme.onSurface,
    )

    Text(
        text = "我是 onSurfaceVariant 色文字（次要文本色）",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    // 给 Box 设置背景色
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.shapes.small,
            )
            .padding(12.dp),
    ) {
        Text(
            text = "primaryContainer 背景 + onPrimaryContainer 文字",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}


// ==================== Step 2: 字体 ====================

/**
 * ## Step 2: 读取主题字体
 *
 * 日常最常用的 4 个：
 * - `typography.titleLarge` → 页面/卡片标题
 * - `typography.titleMedium` → 小标题
 * - `typography.bodyLarge` → 正文（Text 的默认值）
 * - `typography.bodySmall` → 辅助说明文字
 *
 * 可以用 `.copy()` 在主题样式基础上微调：
 * ```
 * MaterialTheme.typography.titleLarge.copy(color = Color.Red)
 * ```
 */
@Composable
private fun Step2_Typography() {
    StepHeader("Step 2", "读取主题字体")

    Text(
        text = "titleLarge — 大标题",
        style = MaterialTheme.typography.titleLarge,
    )

    Text(
        text = "titleMedium — 小标题",
        style = MaterialTheme.typography.titleMedium,
    )

    Text(
        text = "bodyLarge — 正文（Text 默认值）",
        style = MaterialTheme.typography.bodyLarge,
    )

    Text(
        text = "bodySmall — 辅助说明",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Text(
        text = "labelSmall — 最小标签",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.outline,
    )

    // copy() 微调
    Text(
        text = "copy() 微调：titleMedium + error色",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.error,
        ),
    )
}


// ==================== Step 3: 形状 ====================

/**
 * ## Step 3: 读取主题形状
 *
 * - `shapes.small` → Chip、Tag（8dp 圆角）
 * - `shapes.medium` → Card、Dialog（12dp 圆角）
 * - `shapes.large` → 大面积组件（16dp 圆角）
 *
 * Material3 组件已有默认形状，通常不需要手动指定。
 * 手动指定主要用于自定义 Box/Surface。
 */
@Composable
private fun Step3_Shapes() {
    StepHeader("Step 3", "读取主题形状")

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(80.dp, 48.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text("small", style = MaterialTheme.typography.labelSmall)
        }

        Box(
            modifier = Modifier
                .size(80.dp, 48.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text("medium", style = MaterialTheme.typography.labelSmall)
        }

        Box(
            modifier = Modifier
                .size(80.dp, 48.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.large)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text("large", style = MaterialTheme.typography.labelSmall)
        }
    }
}


// ==================== Step 4: 实战 — 个人资料卡片 ====================

/**
 * ## Step 4: 综合实战 — 个人资料卡片
 *
 * 把 colorScheme + typography + shapes 组合起来，构建一个真实的 UI 组件。
 * 注意：没有任何硬编码颜色，全部来自 MaterialTheme。
 */
@Composable
private fun Step4_ProfileCard() {
    StepHeader("Step 4", "实战 — 个人资料卡片")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 头像占位
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "张三",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "Android 开发者",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StatItem(icon = Icons.Default.Star, label = "收藏", count = "128")
                StatItem(icon = Icons.Default.Favorite, label = "点赞", count = "256")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}) {
                    Text("关注")
                }
                OutlinedButton(onClick = {}) {
                    Text("发消息")
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    count: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = count,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}


// ==================== Step 5: 实战 — 设置列表 ====================

/**
 * ## Step 5: 综合实战 — 设置列表
 *
 * 另一个常见 UI：设置页面。
 * 演示 Surface + 组件嵌套时如何统一使用主题。
 */
@Composable
private fun Step5_SettingsList() {
    StepHeader("Step 5", "实战 — 设置列表")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column {
            SettingsItem(title = "账号管理", subtitle = "修改密码、绑定手机")
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            SettingsItem(title = "通知设置", subtitle = "推送、免打扰时间")
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            SettingsItem(title = "关于应用", subtitle = "版本 1.0.0")
        }
    }

    // 使用 error 色的危险操作按钮
    TextButton(onClick = {}) {
        Text(
            text = "退出登录",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun SettingsItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}


// ==================== 工具组件 ====================

@Composable
private fun StepHeader(step: String, title: String) {
    Column {
        Text(
            text = step,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
