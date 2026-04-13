package com.example.android.training.compose.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


// ===========================================================================
// 第一部分：自定义扩展颜色 (Extended Colors via CompositionLocal)
// ===========================================================================

/**
 * # 自定义主题扩展
 *
 * MaterialTheme 的 colorScheme 只包含 Material Design 定义的颜色角色。
 * 但实际项目中，往往需要额外的品牌色、功能色等。
 *
 * ## 方案：CompositionLocal + 扩展属性
 *
 * 1. 定义自定义颜色数据类
 * 2. 创建 CompositionLocal 来持有它
 * 3. 在自定义 Theme 函数中 provide 它
 * 4. 通过扩展属性方便访问
 *
 * ### 为什么用 staticCompositionLocalOf 而不是 compositionLocalOf？
 * - **staticCompositionLocalOf**: 值变化时，所有读取它的组件都会重组（全量重组）。
 *   适用于主题色这种很少变化的值，性能更好（无额外追踪开销）。
 * - **compositionLocalOf**: 值变化时，只有读取了变化部分的组件重组（精确重组）。
 *   适用于频繁变化的值（如滚动位置）。
 */

/** 自定义扩展颜色数据类，使用 @Immutable 告诉 Compose 此类不可变 */
@Immutable
data class ExtendedColors(
    val success: Color = Color.Unspecified,
    val onSuccess: Color = Color.Unspecified,
    val warning: Color = Color.Unspecified,
    val onWarning: Color = Color.Unspecified,
    val info: Color = Color.Unspecified,
    val onInfo: Color = Color.Unspecified,
    val brandPrimary: Color = Color.Unspecified,
    val brandSecondary: Color = Color.Unspecified,
)

/**
 * 使用 staticCompositionLocalOf 创建 CompositionLocal。
 * 默认值为空的 ExtendedColors，如果忘记 provide 会使用 Color.Unspecified。
 */
val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }

private val LightExtendedColors = ExtendedColors(
    success = Color(0xFF2E7D32),
    onSuccess = Color.White,
    warning = Color(0xFFED6C02),
    onWarning = Color.White,
    info = Color(0xFF0288D1),
    onInfo = Color.White,
    brandPrimary = Color(0xFF6200EE),
    brandSecondary = Color(0xFF03DAC6),
)

private val DarkExtendedColors = ExtendedColors(
    success = Color(0xFF66BB6A),
    onSuccess = Color.Black,
    warning = Color(0xFFFFA726),
    onWarning = Color.Black,
    info = Color(0xFF29B6F6),
    onInfo = Color.Black,
    brandPrimary = Color(0xFFBB86FC),
    brandSecondary = Color(0xFF03DAC6),
)


// ===========================================================================
// 第二部分：自定义间距系统 (Custom Spacing)
// ===========================================================================

/**
 * ## 自定义间距系统
 *
 * 和颜色一样，可以定义统一的间距体系，通过 CompositionLocal 注入。
 * 这样所有组件的间距都来自主题，保证一致性，也方便全局调整。
 */

@Immutable
data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }


// ===========================================================================
// 第三部分：组合自定义主题
// ===========================================================================

/**
 * ## 组合自定义主题
 *
 * 将 MaterialTheme + 自定义扩展 组合到一个 Theme 函数中。
 * 外部只需调用 `AppTheme { ... }` 即可获得完整的主题能力。
 *
 * ### CompositionLocalProvider
 * 用于向组件树提供 CompositionLocal 值。
 * 多个 provides 可以同时传入，用 `*arrayOf(...)` 展开或逐个写。
 */
@Composable
fun AppTheme(
    isDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDark) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onBackground = Color.White,
            onSurface = Color.White,
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFFF5F5F5),
            surface = Color.White,
        )
    }

    val extendedColors = if (isDark) DarkExtendedColors else LightExtendedColors

    /**
     * CompositionLocalProvider 在此处同时提供多个自定义 Local 值。
     * MaterialTheme 内部也是通过 CompositionLocalProvider 提供 colorScheme 等。
     */
    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
        LocalSpacing provides Spacing(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

/**
 * ## 扩展属性：方便访问自定义主题值
 *
 * 通过给 MaterialTheme 添加扩展属性，使访问方式与官方 API 完全一致：
 * - `MaterialTheme.colorScheme.primary` → 官方颜色
 * - `MaterialTheme.extendedColors.success` → 自定义颜色
 * - `MaterialTheme.spacing.medium` → 自定义间距
 *
 * 这是 Compose 主题的最佳实践。
 */
val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current

val MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current


// ===========================================================================
// 第四部分：动态切换主题 Demo
// ===========================================================================

/**
 * ## 动态切换主题
 *
 * 主题切换的核心原理非常简单：
 * 1. 用一个 State 保存当前主题模式（如 isDark）
 * 2. 将 State 传给 Theme 函数
 * 3. State 变化时，MaterialTheme 重组，所有子组件自动读取新的颜色
 *
 * Compose 的声明式特性使得主题切换天然支持，无需手动刷新。
 *
 * ### 配合动画
 * 使用 animateColorAsState 可以让颜色变化有平滑过渡效果。
 */
@Preview(showBackground = true, widthDp = 420)
@Composable
private fun CustomThemePreview() {
    var isDark by remember { mutableStateOf(false) }

    AppTheme(isDark = isDark) {
        CustomThemeTrainingScreen(
            isDark = isDark,
            onToggleTheme = { isDark = !isDark },
        )
    }
}

@Composable
fun CustomThemeTrainingScreen(
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {
            // ---- 主题切换 ----
            ThemeSwitchSection(isDark, onToggleTheme)

            // ---- 扩展颜色演示 ----
            ExtendedColorsSection()

            // ---- 自定义间距演示 ----
            SpacingSection()

            // ---- 主题颜色动画 ----
            AnimatedThemeSection(isDark)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// ==================== 主题切换 ====================

@Composable
private fun ThemeSwitchSection(isDark: Boolean, onToggle: () -> Unit) {
    SectionHeader2("1. 动态切换主题")

    Text(
        text = "当前: ${if (isDark) "深色模式" else "浅色模式"}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )

    Button(onClick = onToggle) {
        Text(if (isDark) "切换到浅色" else "切换到深色")
    }

    Text(
        text = "切换后所有组件颜色自动变化，因为它们都读取的是 MaterialTheme.colorScheme",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}


// ==================== 扩展颜色演示 ====================

/**
 * 演示通过 MaterialTheme.extendedColors 访问自定义颜色。
 */
@Composable
private fun ExtendedColorsSection() {
    SectionHeader2("2. 自定义扩展颜色")

    Text(
        text = "通过 MaterialTheme.extendedColors.xxx 访问，用法与官方 API 一致",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    val ext = MaterialTheme.extendedColors

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ExtColorChip("success", ext.success, ext.onSuccess)
        ExtColorChip("warning", ext.warning, ext.onWarning)
        ExtColorChip("info", ext.info, ext.onInfo)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ExtColorChip("brand 1", ext.brandPrimary, Color.White)
        ExtColorChip("brand 2", ext.brandSecondary, Color.Black)
    }

    /**
     * 实际使用示例：Success 状态的 Card
     */
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ext.success,
            contentColor = ext.onSuccess,
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("操作成功!", style = MaterialTheme.typography.titleMedium)
            Text("使用 extendedColors.success 作为背景色", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun ExtColorChip(label: String, bg: Color, fg: Color) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 48.dp)
            .background(bg, RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, color = fg, style = MaterialTheme.typography.labelSmall)
    }
}


// ==================== 自定义间距演示 ====================

@Composable
private fun SpacingSection() {
    SectionHeader2("3. 自定义间距系统")

    Text(
        text = "通过 MaterialTheme.spacing.xxx 获取统一间距",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    val sp = MaterialTheme.spacing

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        SpacingBar("extraSmall (${sp.extraSmall})", sp.extraSmall)
        SpacingBar("small (${sp.small})", sp.small)
        SpacingBar("medium (${sp.medium})", sp.medium)
        SpacingBar("large (${sp.large})", sp.large)
        SpacingBar("extraLarge (${sp.extraLarge})", sp.extraLarge)
    }

    Text(
        text = "示例：padding(MaterialTheme.spacing.medium) 替代硬编码 padding(16.dp)",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.outline,
    )
}

@Composable
private fun SpacingBar(label: String, width: Dp) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(width = width * 3, height = 24.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp)),
        )
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}


// ==================== 主题颜色动画 ====================

/**
 * ## 主题切换 + 颜色动画
 *
 * animateColorAsState 可以让颜色在主题切换时平滑过渡。
 * 适用场景：需要细粒度控制某些颜色变化动画时。
 *
 * 注意：如果整个主题切换都要动画，需要对每个用到的颜色都做 animate，
 * 工程量较大。通常只对关键的 UI 元素做动画。
 */
@Composable
private fun AnimatedThemeSection(isDark: Boolean) {
    SectionHeader2("4. 主题颜色动画过渡")

    Text(
        text = "使用 animateColorAsState 实现颜色平滑过渡",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    val animatedBg by animateColorAsState(
        targetValue = if (isDark) Color(0xFF1E1E2E) else Color(0xFFE8DEF8),
        animationSpec = tween(durationMillis = 600),
        label = "bg",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (isDark) Color(0xFFCDD6F4) else Color(0xFF1C1B1F),
        animationSpec = tween(durationMillis = 600),
        label = "content",
    )
    val animatedAccent by animateColorAsState(
        targetValue = if (isDark) Color(0xFFF38BA8) else Color(0xFF6750A4),
        animationSpec = tween(durationMillis = 600),
        label = "accent",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(animatedBg, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                "颜色在 600ms 内平滑过渡",
                style = MaterialTheme.typography.titleMedium,
                color = animatedContent,
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(animatedAccent),
            )
        }
    }
}


// ===========================================================================
// 第五部分：最佳实践总结
// ===========================================================================

/**
 * # Compose Theme 最佳实践
 *
 * ## 1. 永远通过 MaterialTheme 读取颜色，不要硬编码
 * ```
 * // BAD
 * Text(color = Color(0xFF333333))
 *
 * // GOOD
 * Text(color = MaterialTheme.colorScheme.onSurface)
 * ```
 *
 * ## 2. 项目自定义颜色通过 CompositionLocal 扩展
 * ```
 * val MaterialTheme.extendedColors: ExtendedColors
 *     @Composable get() = LocalExtendedColors.current
 * ```
 *
 * ## 3. 主题配置集中管理
 * - Color.kt → 定义颜色常量
 * - Type.kt → 定义 Typography
 * - Shape.kt → 定义 Shapes
 * - Theme.kt → 组装 MaterialTheme + 扩展
 *
 * ## 4. 使用 copy() 微调样式而非重新创建
 * ```
 * MaterialTheme.typography.titleLarge.copy(
 *     color = MaterialTheme.colorScheme.error
 * )
 * ```
 *
 * ## 5. Preview 中使用主题包裹
 * ```
 * @Preview
 * @Composable
 * fun Preview() {
 *     AppTheme {
 *         MyComponent()
 *     }
 * }
 * ```
 * 不包裹主题的 Preview 会使用默认的 Material 配色，可能与实际效果不同。
 *
 * ## 6. 使用 Material Theme Builder 工具
 * 官方提供了 Material Theme Builder 网页工具，
 * 输入品牌色即可自动生成完整的 Light/Dark ColorScheme 代码：
 * https://m3.material.io/theme-builder
 */


// ==================== 工具组件 ====================

@Composable
private fun SectionHeader2(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
    )
}
