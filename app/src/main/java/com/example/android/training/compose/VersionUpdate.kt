package com.example.android.training.compose

/**
 * v1.9 要求使用 Android Gradle Plugin (AGP) 和 Lint 的 8.8.2 或更高版本
 * 博客: https://mp.weixin.qq.com/s/_IIeoV0iGCVeC8-I0IEmmw
 * implementation(platform("androidx.compose:compose-bom:2025.08.00"))
 *
 * 增强 Crash 分析:
 * Composer.setDiagnosticStackTraceEnabled(BuildConfig.DEBUG)
 * 新阴影api:
 * Modifier.dropShadow() Modifier.innerShadow()
 * 可见性api:
 * Modifier.onVisibilityChanged Modifier.onFirstVisible
 * 富文本样式支持:
 * BasicTextField支持OutputTransformation
 * 新增的TextFieldBuffer.addStyle()方法允许应用SpanStyle或ParagraphStyle来更改文本外观，而不影响底层的TextFieldState
 * LazyLayout:
 * 改进LazyLayout 的所有构建块现已稳定，包括LazyLayoutMeasurePolicy、LazyLayoutItemProvider和LazyLayoutPrefetchState，
 * 开发者现在可以构建自己的自定义 Lazy 组件。
 * 预加载性能优化:
 * 通过引入新的预加载行为，Lazy List 和 Lazy Grid 的滚动性能得到显著提升。
 * 现在可以通过LazyLayoutCacheWindow自定义预加载的前向项目数量和保留的后向项目数量
 * 二维滚动api:
 * 继Draggable2D之后，本次更新推出了Scrollable2D，为 Compose 添加了完整的二维滚动功能。
 * 该 API 支持同时在两个方向上的滚动和滑动操作，适用于电子表格或图片查看器等复杂布局
 * 滚动互操作性改:
 * 进本次更新包含多项与 View 系统滚动和嵌套滚动互操作性的改进：
 * 现在可使用ViewTreeObserver#onScrollChangeListener监听 Compose 滚动事件
 * 修复了 Compose 和 View 之间在滑动动画过程中速度分发错误的问题
 * Compose现在以正确顺序调用视图的嵌套滚动回调
 * 在 AndroidView 中嵌套 NestedScrollView 时，嵌套滚动功能得到正确支持
 * 新注解和 Lint 检查:
 * 新版本引入了运行时注解库，将@Stable、@Immutable和@StableMarker注解移至runtime-annotation模块，
 * 使非 Compose 模块也能使用这些注解。
 * 新增了两个注解及相应的代码检查：
 * @RememberInComposition: 标记不得在 @Composable 内部直接调用的构造函数、函数和属性获取器
 * @FrequentlyChangingValue: 标记不应在 @Composable 内部直接调用的函数和属性获取器，防止频繁重新组合
 * 新增两个 ContextMenu 相关 API：
 * Modifier.appendTextContextMenuComponents(): 向上下文菜单添加新项
 * Modifier.filterTextContextMenuComponents(): 从上下文菜单中移除项
 * */

