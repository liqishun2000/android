package com.example.android.training.compose.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # Compose MaterialTheme 主题系统详解
 *
 * MaterialTheme 是 Compose 中的核心主题系统，它通过 [CompositionLocal] 向组件树传递主题配置。
 * 所有 Material3 组件都会自动从 MaterialTheme 中读取样式。
 *
 * ## 三大支柱 (Three Pillars)
 *
 * 1. **colorScheme** — 颜色方案：定义应用的所有颜色（primary, secondary, surface, background 等）
 * 2. **typography** — 字体排版：定义文本样式（displayLarge, headlineMedium, bodySmall 等）
 * 3. **shapes** — 形状：定义组件的圆角/裁切形状（extraSmall, small, medium, large 等）
 *
 * ## 核心原理
 *
 * MaterialTheme 本质是通过 CompositionLocal 提供主题值：
 * - [MaterialTheme.colorScheme] → LocalColorScheme
 * - [MaterialTheme.typography] → LocalTypography
 * - [MaterialTheme.shapes] → LocalShapes
 *
 * 任何子组件都可以通过 `MaterialTheme.colorScheme.primary` 等方式读取当前主题值。
 * Material3 的所有组件（Button, Card, Text 等）内部都使用了这些值作为默认样式。
 */


// ==================== 1. 颜色方案 (ColorScheme) ====================

/**
 * ## ColorScheme 颜色方案
 *
 * Material3 的 ColorScheme 包含约 29 种颜色角色（Color Role），分为几大组：
 *
 * ### Primary 组（主色）
 * - **primary**: 主要按钮、FAB、强调元素的颜色
 * - **onPrimary**: primary 上面的内容色（如按钮文字）
 * - **primaryContainer**: 主色容器背景（较浅）
 * - **onPrimaryContainer**: primaryContainer 上面的内容色
 *
 * ### Secondary 组（辅助色）
 * - **secondary / onSecondary / secondaryContainer / onSecondaryContainer**
 * - 用于次要操作，如 FilterChip、NavigationBar 选中项等
 *
 * ### Tertiary 组（第三色）
 * - **tertiary / onTertiary / tertiaryContainer / onTertiaryContainer**
 * - 用于补充强调，平衡 primary 和 secondary
 *
 * ### Error 组（错误色）
 * - **error / onError / errorContainer / onErrorContainer**
 * - 用于错误提示
 *
 * ### Surface 组（表面色）— 最常用
 * - **surface**: 卡片、对话框等表面的背景色
 * - **onSurface**: surface 上面的内容色（最主要的文本色）
 * - **surfaceVariant**: 变体表面色
 * - **onSurfaceVariant**: 变体表面上的内容色（次要文本色）
 * - **surfaceContainerLowest / Low / Medium / High / Highest**:
 *   不同层级的表面容器色（Material3 新增，替代旧的 elevation overlay）
 *
 * ### Background & Outline
 * - **background / onBackground**: 整体页面背景
 * - **outline**: 分割线、边框色
 * - **outlineVariant**: 较浅的分割线
 *
 * ### 关于 on* 前缀
 * on* 颜色始终用于 "在其对应色上面的内容"，确保对比度和可读性。
 * 例如：primary=蓝色 → onPrimary=白色（蓝色背景上白色文字）
 */

private val SampleLightColors = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF7D5260),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
)

private val SampleDarkColors = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
)


// ==================== 2. 字体排版 (Typography) ====================

/**
 * ## Typography 字体排版
 *
 * Material3 定义了 5 大类 × 3 种尺寸 = 15 种文本样式：
 *
 * | 类别        | Large        | Medium        | Small        |
 * |------------|-------------|--------------|-------------|
 * | Display    | displayLarge | displayMedium | displaySmall |
 * | Headline   | headlineLarge| headlineMedium| headlineSmall|
 * | Title      | titleLarge   | titleMedium   | titleSmall   |
 * | Body       | bodyLarge    | bodyMedium    | bodySmall    |
 * | Label      | labelLarge   | labelMedium   | labelSmall   |
 *
 * ### 各类别典型用途
 * - **Display**: 最大的文字，首页英雄区域标题
 * - **Headline**: 章节标题
 * - **Title**: 卡片标题、AppBar 标题
 * - **Body**: 正文内容（bodyLarge 是默认的 Text 样式）
 * - **Label**: 按钮文字、Tab 文字、小标签
 *
 * ### 使用方式
 * ```
 * Text(
 *     text = "Hello",
 *     style = MaterialTheme.typography.headlineMedium
 * )
 * ```
 */

private val SampleTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)


// ==================== 3. 形状 (Shapes) ====================

/**
 * ## Shapes 形状系统
 *
 * Material3 定义了 5 种尺寸等级的形状，用于不同大小的组件：
 *
 * - **extraSmall** (4.dp): 最小组件，如 Menu
 * - **small** (8.dp): 小组件，如 Chip、Snackbar
 * - **medium** (12.dp): 中等组件，如 Card、AlertDialog
 * - **large** (16.dp): 大组件，如 NavigationDrawer
 * - **extraLarge** (28.dp): 最大组件，如 BottomSheet
 *
 * 形状不仅限于 RoundedCornerShape，也可以使用 CutCornerShape（切角）等。
 *
 * ### Material3 组件默认使用的 Shape
 * - Button → shape.full (Stadium/Capsule)
 * - Card → shape.medium
 * - BottomSheet → shape.extraLarge (只圆上面两个角)
 * - Chip → shape.small
 */

private val SampleShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
)


// ==================== Demo 主题函数 ====================

/**
 * ## MaterialTheme 使用方式
 *
 * 通常在 Activity 的 setContent 中包裹整个 UI：
 * ```
 * setContent {
 *     MyAppTheme {
 *         // 所有子组件都可以访问主题
 *         MyScreen()
 *     }
 * }
 * ```
 *
 * ### dynamicColor (Android 12+)
 * Android 12 引入了 Dynamic Color，可以从用户壁纸提取颜色自动生成配色方案。
 * 使用 [dynamicLightColorScheme] / [dynamicDarkColorScheme] 来获取系统动态配色。
 * 低于 Android 12 的设备会 fallback 到自定义配色。
 *
 * ### 嵌套主题
 * MaterialTheme 可以嵌套使用，内层会覆盖外层：
 * ```
 * MaterialTheme(colorScheme = lightColors) {
 *     // 这里是浅色主题
 *     MaterialTheme(colorScheme = darkColors) {
 *         // 这里变成了深色主题，但 typography 和 shapes 仍继承外层
 *     }
 * }
 * ```
 */
@Composable
fun SampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    /**
     * 配色选择优先级：
     * 1. 如果 dynamicColor=true 且 Android 12+，使用壁纸动态配色
     * 2. 否则根据 darkTheme 选择预定义的深色/浅色方案
     */
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> SampleDarkColors
        else -> SampleLightColors
    }

    /**
     * MaterialTheme 会将 colorScheme / typography / shapes 通过 CompositionLocal 注入组件树。
     * 子组件通过 MaterialTheme.colorScheme / .typography / .shapes 读取。
     */
    MaterialTheme(
        colorScheme = colorScheme,
        typography = SampleTypography,
        shapes = SampleShapes,
        content = content,
    )
}


// ==================== 完整示例 ====================

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, widthDp = 420)
@Composable
private fun ThemeTrainingPreview() {
    SampleTheme(dynamicColor = false) {
        ThemeTrainingScreen()
    }
}

@Preview(showBackground = true, widthDp = 420, name = "Dark Theme")
@Composable
private fun ThemeTrainingDarkPreview() {
    SampleTheme(darkTheme = true, dynamicColor = false) {
        ThemeTrainingScreen()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeTrainingScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // ---- 颜色演示 ----
            ColorSchemeSection()

            // ---- 字体演示 ----
            TypographySection()

            // ---- 形状演示 ----
            ShapesSection()

            // ---- 实际组件如何自动适配主题 ----
            ComponentsWithThemeSection()

            // ---- 嵌套主题演示 ----
            NestedThemeSection()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// ==================== 颜色方案演示 ====================

/**
 * 展示 MaterialTheme.colorScheme 中各颜色角色的实际效果。
 * 所有颜色都通过 `MaterialTheme.colorScheme.xxx` 获取，而非硬编码。
 * 这样切换主题时颜色会自动变化。
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorSchemeSection() {
    SectionHeader("1. ColorScheme 颜色方案")

    Text(
        text = "通过 MaterialTheme.colorScheme.xxx 获取颜色，组件会自动适配深色/浅色主题",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ColorSwatch("primary", MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
        ColorSwatch("secondary", MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
        ColorSwatch("tertiary", MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onTertiary)
        ColorSwatch("error", MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.onError)
        ColorSwatch("primaryContainer", MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
        ColorSwatch("secondaryContainer", MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
        ColorSwatch("surface", MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.onSurface)
        ColorSwatch("surfaceVariant", MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Text(
        text = "提示：on* 颜色用于对应色之上的内容，如 onPrimary 是 primary 背景上的文字色",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.outline,
    )
}

@Composable
private fun ColorSwatch(name: String, bgColor: Color, contentColor: Color) {
    Box(
        modifier = Modifier
            .size(width = 120.dp, height = 56.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name,
            color = contentColor,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}


// ==================== 字体排版演示 ====================

/**
 * 展示 MaterialTheme.typography 中各种文本样式。
 *
 * 使用方式：
 * ```
 * Text(
 *     text = "标题",
 *     style = MaterialTheme.typography.titleLarge
 * )
 * ```
 *
 * Text 组件的默认 style 是 bodyLarge，所以不指定 style 时就是 bodyLarge。
 */
@Composable
private fun TypographySection() {
    SectionHeader("2. Typography 字体排版")

    Text(
        text = "通过 MaterialTheme.typography.xxx 获取文本样式",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    /**
     * Text 默认使用 LocalTextStyle（由 ProvideTextStyle 设置），
     * 如果没有 ProvideTextStyle 包裹，默认就是 bodyLarge。
     * 显式传入 style 可以覆盖默认值。
     */
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Display Large (57sp)", style = MaterialTheme.typography.displayLarge)
        Text("Headline Medium (28sp)", style = MaterialTheme.typography.headlineMedium)
        Text("Title Large (22sp)", style = MaterialTheme.typography.titleLarge)
        Text("Title Medium (16sp)", style = MaterialTheme.typography.titleMedium)
        Text("Body Large (16sp) — 默认", style = MaterialTheme.typography.bodyLarge)
        Text("Body Medium (14sp)", style = MaterialTheme.typography.bodyMedium)
        Text("Label Large (14sp)", style = MaterialTheme.typography.labelLarge)
        Text("Label Small (11sp)", style = MaterialTheme.typography.labelSmall)
    }

    /**
     * 可以基于主题样式做微调，使用 copy() 修改个别属性：
     */
    Text(
        text = "基于 titleLarge 微调: copy(color = error)",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.error,
        ),
    )
}


// ==================== 形状演示 ====================

/**
 * 展示 MaterialTheme.shapes 中各种形状。
 *
 * 使用方式：
 * ```
 * Card(shape = MaterialTheme.shapes.medium) { ... }
 * ```
 *
 * 大多数 Material3 组件已经有默认的 shape 参数，
 * 通常不需要手动指定，除非要自定义。
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ShapesSection() {
    SectionHeader("3. Shapes 形状系统")

    Text(
        text = "通过 MaterialTheme.shapes.xxx 获取形状",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ShapeSample("extraSmall", MaterialTheme.shapes.extraSmall)
        ShapeSample("small", MaterialTheme.shapes.small)
        ShapeSample("medium", MaterialTheme.shapes.medium)
        ShapeSample("large", MaterialTheme.shapes.large)
        ShapeSample("extraLarge", MaterialTheme.shapes.extraLarge)
    }

    Text(
        text = "形状也支持 CutCornerShape（切角）：",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Box(
        modifier = Modifier
            .size(120.dp, 56.dp)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                CutCornerShape(12.dp),
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            "CutCorner",
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun ShapeSample(name: String, shape: androidx.compose.ui.graphics.Shape) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}


// ==================== 组件自动适配主题 ====================

/**
 * Material3 组件（Button, Card, Text 等）会自动使用 MaterialTheme 的值。
 *
 * 例如：
 * - Button 的背景色默认是 colorScheme.primary
 * - Button 文字颜色默认是 colorScheme.onPrimary
 * - Card 的背景色默认是 colorScheme.surfaceContainerLow（或 surface）
 * - Card 形状默认是 shapes.medium
 *
 * 如果需要覆盖，可以通过组件自身的 colors / shape 参数。
 */
@Composable
private fun ComponentsWithThemeSection() {
    SectionHeader("4. 组件自动适配主题")

    Text(
        text = "Material3 组件会自动使用 MaterialTheme 中的颜色、字体和形状",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        /** Button 默认使用 primary 色 */
        Button(onClick = {}) {
            Text("Primary Button")
        }
    }

    /** Card 默认使用 surface 色 + medium 形状 */
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Card 标题", style = MaterialTheme.typography.titleMedium)
            Text(
                "Card 默认使用 surfaceContainerLow 和 shapes.medium",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

    /** ElevatedCard 有阴影效果 */
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ElevatedCard", style = MaterialTheme.typography.titleMedium)
            Text("带阴影的卡片", style = MaterialTheme.typography.bodySmall)
        }
    }

    /** OutlinedCard 有边框 */
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("OutlinedCard", style = MaterialTheme.typography.titleMedium)
            Text("边框颜色使用 outline", style = MaterialTheme.typography.bodySmall)
        }
    }

    /**
     * 覆盖组件默认主题色：
     * 通过 colors 参数可以单独覆盖某个组件的颜色，而不影响全局主题。
     */
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
        ),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("自定义 Card 颜色", style = MaterialTheme.typography.titleMedium)
            Text(
                "通过 CardDefaults.cardColors() 覆盖默认颜色",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


// ==================== 嵌套主题 ====================

/**
 * ## 嵌套主题 (Nested Theme)
 *
 * MaterialTheme 可以嵌套使用。内层的 MaterialTheme 只覆盖你传入的参数，
 * 未传入的参数会继承外层主题。
 *
 * 典型场景：
 * - 某个区域需要反转深色/浅色
 * - 某个 Section 需要使用不同的配色方案
 * - 对话框或底部弹窗使用不同主题
 *
 * 注意：嵌套主题会创建新的 CompositionLocal 作用域，
 * 内层组件看到的 MaterialTheme.colorScheme 等是内层的值。
 */
@Composable
private fun NestedThemeSection() {
    SectionHeader("5. 嵌套主题（局部覆盖）")

    Text(
        text = "外层: Light 主题区域",
        style = MaterialTheme.typography.bodyMedium,
    )

    /**
     * 只覆盖 colorScheme，typography 和 shapes 继承外层。
     * 内层 Surface/Card 等组件会自动使用新的深色配色。
     */
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFFBB86FC),
            surface = Color(0xFF121212),
            onSurface = Color.White,
        ),
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "内层: Dark 主题区域",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "这里的组件使用了嵌套的深色主题配色，但字体和形状继承了外层",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}) {
                    Text("嵌套主题中的按钮")
                }
            }
        }
    }
}


// ==================== 工具组件 ====================

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
    )
}
