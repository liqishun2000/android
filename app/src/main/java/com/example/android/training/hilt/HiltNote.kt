package com.example.android.training.hilt

/**
 * ╔══════════════════════════════════════════════════════════════════════════════╗
 * ║                         Hilt 依赖注入框架详解                                ║
 * ╚══════════════════════════════════════════════════════════════════════════════╝
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 一、什么是 Hilt？                                                            │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * Hilt 是 Google 推荐的 Android 依赖注入框架，基于 Dagger 构建。
 * 它简化了 Dagger 在 Android 中的使用，提供了一套标准化的组件和作用域，
 * 让开发者无需手动创建 Component 和 Subcomponent。
 *
 * 核心价值：
 * - 减少模板代码：相比纯 Dagger，代码量减少 60%+
 * - 标准化：统一的组件层级，团队协作更容易
 * - 生命周期感知：组件自动绑定 Android 类的生命周期
 * - 可测试性：轻松替换依赖进行测试
 * - 编译时验证：所有依赖关系在编译时检查，不会在运行时崩溃
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 二、依赖注入（DI）基础概念                                                    │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * 「依赖注入」是一种设计模式：类不自己创建依赖，而是从外部接收。
 *
 * ❌ 没有 DI（紧耦合）：
 *    class UserViewModel {
 *        private val repository = UserRepositoryImpl()   // 直接创建，难以替换和测试
 *    }
 *
 * ✅ 使用 DI（松耦合）：
 *    class UserViewModel(
 *        private val repository: UserRepository   // 从外部注入，可以传入任何实现
 *    )
 *
 * DI 的好处：
 * - 代码更容易测试（可以注入 Mock 对象）
 * - 降低耦合度（依赖接口而非实现）
 * - 更容易替换实现（如切换网络库、数据库等）
 * - 更好的代码复用
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 三、Hilt 核心注解详解                                                        │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * 1. @HiltAndroidApp
 *    - 标注在 Application 类上
 *    - 触发 Hilt 代码生成，创建应用级别的依赖容器
 *    - 整个 App 有且只有一个
 *    - 示例：
 *      @HiltAndroidApp
 *      class MyApp : Application()
 *
 * 2. @AndroidEntryPoint
 *    - 标注在 Android 类上（Activity、Fragment、Service、BroadcastReceiver、View）
 *    - 使该类成为依赖注入的目标，可以使用 @Inject 接收依赖
 *    - 如果 Fragment 使用了此注解，其宿主 Activity 也必须使用
 *    - 示例：
 *      @AndroidEntryPoint
 *      class MainActivity : ComponentActivity()
 *
 * 3. @Inject
 *    - 两种用法：
 *      a) 构造函数注入（推荐）：告诉 Hilt 如何创建某个类的实例
 *         class UserRepository @Inject constructor(private val api: ApiService)
 *
 *      b) 字段注入：在 Android 类中注入依赖（因为 Android 类的构造函数由系统控制）
 *         @Inject lateinit var logger: Logger   // 不能是 private
 *
 * 4. @Module
 *    - 标注在类上，表示这是一个 Hilt 模块
 *    - 模块告诉 Hilt 如何提供某些类型的实例
 *    - 必须配合 @InstallIn 使用
 *
 * 5. @InstallIn
 *    - 指定 Module 安装到哪个 Hilt 组件中
 *    - 决定了 Module 中提供的依赖的可用范围和生命周期
 *    - 示例：@InstallIn(SingletonComponent::class)
 *
 * 6. @Provides
 *    - 在 @Module 中使用，告诉 Hilt 如何创建无法通过构造函数注入的类型
 *    - 典型场景：第三方库实例、Builder 模式创建的对象、接口实例
 *    - 方法的返回类型就是提供的类型，参数由 Hilt 自动注入
 *
 * 7. @Binds
 *    - 在 @Module 中使用，将接口绑定到具体实现
 *    - 比 @Provides 更高效（不生成额外工厂类）
 *    - 方法必须是抽象的：参数是实现类，返回类型是接口
 *    - 要求：Module 必须是 abstract class
 *
 * 8. @Singleton / @XxxScoped
 *    - 作用域注解，控制实例在对应组件内是否为单例
 *    - 不加作用域 = 每次注入创建新实例
 *    - 加了作用域 = 在组件生命周期内只创建一次
 *
 * 9. @Qualifier
 *    - 当同一类型有多个实现时，用自定义 Qualifier 注解区分
 *    - 定义方式：
 *      @Qualifier
 *      @Retention(AnnotationRetention.BINARY)
 *      annotation class RemoteData
 *
 * 10. @HiltViewModel
 *     - 标注在 ViewModel 上，使其支持 Hilt 注入
 *     - ViewModel 通过 @Inject constructor 接收依赖
 *     - 在 Compose 中用 hiltViewModel()，在 Activity/Fragment 中用 by viewModels()
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 四、Hilt 组件层级与作用域                                                     │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * Hilt 预定义了一组组件，形成层级关系（父组件的依赖对子组件可见）：
 *
 *                    SingletonComponent (@Singleton)
 *                     ├── ActivityRetainedComponent (@ActivityRetainedScoped)
 *                     │    └── ViewModelComponent (@ViewModelScoped)
 *                     │         └── ActivityComponent (@ActivityScoped)
 *                     │              ├── FragmentComponent (@FragmentScoped)
 *                     │              └── ViewComponent (@ViewScoped)
 *                     │                   └── ViewWithFragmentComponent (@ViewScoped)
 *                     └── ServiceComponent (@ServiceScoped)
 *
 * 组件生命周期：
 * ┌────────────────────────────┬──────────────────────────┬─────────────────────┐
 * │ 组件                       │ 作用域                    │ 创建时机 → 销毁时机  │
 * ├────────────────────────────┼──────────────────────────┼─────────────────────┤
 * │ SingletonComponent         │ @Singleton               │ App onCreate → 终止 │
 * │ ActivityRetainedComponent  │ @ActivityRetainedScoped  │ Activity 创建 → 销毁│
 * │                            │                          │ (配置变更不销毁)     │
 * │ ViewModelComponent         │ @ViewModelScoped         │ ViewModel 创建→清除 │
 * │ ActivityComponent          │ @ActivityScoped          │ Activity onCreate→  │
 * │                            │                          │ onDestroy           │
 * │ FragmentComponent          │ @FragmentScoped          │ Fragment onAttach→  │
 * │                            │                          │ onDestroy           │
 * │ ViewComponent              │ @ViewScoped              │ View 创建 → 销毁    │
 * │ ServiceComponent           │ @ServiceScoped           │ Service onCreate→   │
 * │                            │                          │ onDestroy           │
 * └────────────────────────────┴──────────────────────────┴─────────────────────┘
 *
 * 默认绑定（每个组件自动可用的依赖）：
 * - SingletonComponent  → Application, @ApplicationContext Context
 * - ActivityComponent   → Activity, @ActivityContext Context
 * - FragmentComponent   → Fragment
 * - ViewComponent       → View
 * - ViewModelComponent  → SavedStateHandle
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 五、@Provides vs @Binds 选择指南                                              │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * @Provides:
 * - 第三方库实例（Retrofit、OkHttpClient、Room Database）
 * - 需要配置/构建的对象
 * - 基本类型、String 等
 * - 方法体中有逻辑代码
 * - Module 用 object（或 class + companion object）
 *
 * @Binds:
 * - 接口 → 实现类的绑定
 * - 实现类已有 @Inject constructor
 * - 方法体无逻辑，纯粹的类型映射
 * - Module 必须用 abstract class
 * - 比 @Provides 性能更好（编译产物更少）
 *
 * 混合使用：一个 Module 中同时需要 @Provides 和 @Binds 时，
 * 将 @Provides 放在 companion object 中：
 *
 *   @Module
 *   @InstallIn(SingletonComponent::class)
 *   abstract class MyModule {
 *       @Binds abstract fun bindRepo(impl: RepoImpl): Repo
 *
 *       companion object {
 *           @Provides fun provideApiService(): ApiService = ApiService()
 *       }
 *   }
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 六、Assisted Injection（辅助注入）                                             │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * 当构造函数中同时需要：
 * - Hilt 管理的依赖（编译时已知）
 * - 运行时参数（调用时才知道）
 *
 * 步骤：
 * 1. 构造函数用 @AssistedInject（而非 @Inject）
 * 2. 运行时参数用 @Assisted 标注
 * 3. 定义 @AssistedFactory 接口，工厂方法参数对应 @Assisted 参数
 * 4. 注入工厂接口，调用 create() 创建实例
 *
 * 示例：
 *   class Worker @AssistedInject constructor(
 *       @Assisted private val taskId: String,   // 运行时参数
 *       private val repository: Repository      // Hilt 注入
 *   )
 *
 *   @AssistedFactory
 *   interface WorkerFactory {
 *       fun create(taskId: String): Worker
 *   }
 *
 *   // 使用
 *   @Inject lateinit var factory: WorkerFactory
 *   val worker = factory.create("task-123")
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 七、@EntryPoint（入口点）                                                     │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * Hilt 不支持直接注入的类（ContentProvider、第三方库回调等），
 * 可以通过 @EntryPoint 获取依赖。
 *
 *   @EntryPoint
 *   @InstallIn(SingletonComponent::class)
 *   interface MyEntryPoint {
 *       fun getRepository(): UserRepository
 *   }
 *
 *   // 使用
 *   val entryPoint = EntryPointAccessors.fromApplication(context, MyEntryPoint::class.java)
 *   val repo = entryPoint.getRepository()
 *
 * 不同组件对应不同的 Accessors 方法：
 * - SingletonComponent → EntryPointAccessors.fromApplication(context, ...)
 * - ActivityComponent  → EntryPointAccessors.fromActivity(activity, ...)
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 八、Hilt 与 Compose 集成                                                     │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * 依赖：androidx.hilt:hilt-navigation-compose
 *
 * 在 @Composable 函数中获取 ViewModel：
 *   @Composable
 *   fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
 *       val users by viewModel.users.collectAsState()
 *       // ...
 *   }
 *
 * 在 Navigation 中每个目的地获取独立的 ViewModel：
 *   NavHost(navController, startDestination = "home") {
 *       composable("home") {
 *           val vm: HomeViewModel = hiltViewModel()  // 生命周期绑定到这个 NavBackStackEntry
 *       }
 *       composable("detail/{id}") {
 *           val vm: DetailViewModel = hiltViewModel()
 *       }
 *   }
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 九、Hilt 测试支持                                                            │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * 1. 单元测试 —— 不需要 Hilt
 *    直接通过构造函数传入 Mock 依赖即可：
 *    val vm = UserViewModel(FakeRepository(), FakeLogger())
 *
 * 2. UI / 集成测试 —— @HiltAndroidTest
 *    添加测试依赖：
 *    androidTestImplementation("com.google.dagger:hilt-android-testing:2.56.2")
 *    kspAndroidTest("com.google.dagger:hilt-compiler:2.56.2")
 *
 *    @HiltAndroidTest
 *    @RunWith(AndroidJUnit4::class)
 *    class UserActivityTest {
 *        @get:Rule var hiltRule = HiltAndroidRule(this)
 *
 *        @Inject lateinit var repository: UserRepository
 *
 *        @Before fun setup() {
 *            hiltRule.inject()   // 注入依赖
 *        }
 *    }
 *
 * 3. 替换依赖的三种方式：
 *    a) @BindValue —— 替换单个绑定
 *       @BindValue val repo: UserRepository = FakeRepository()
 *
 *    b) @UninstallModules —— 卸载某个 Module，然后提供替代
 *       @UninstallModules(RepositoryModule::class)
 *       @HiltAndroidTest
 *       class MyTest { ... }
 *
 *    c) @TestInstallIn —— 自动替换，所有测试生效
 *       @TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoryModule::class])
 *       @Module
 *       abstract class FakeModule { ... }
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 十、多模块项目最佳实践                                                        │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * 1. 只有 app 模块的 Application 需要 @HiltAndroidApp
 * 2. 每个模块都需要添加 Hilt 插件和依赖
 * 3. 建议每个模块的 @Module 类放在 di/ 包下
 * 4. 接口定义在上层模块，实现在下层模块，绑定在 app 模块
 *
 * 推荐目录结构：
 *   feature-user/
 *   ├── di/
 *   │   └── UserModule.kt          // @Module @InstallIn
 *   ├── data/
 *   │   ├── UserRepository.kt      // 接口
 *   │   └── UserRepositoryImpl.kt  // 实现, @Inject constructor
 *   ├── domain/
 *   │   └── GetUserUseCase.kt      // @Inject constructor
 *   └── ui/
 *       ├── UserViewModel.kt       // @HiltViewModel
 *       └── UserScreen.kt          // Composable, hiltViewModel()
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 十一、常见问题与注意事项                                                       │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * Q1: @Inject 字段不能是 private，为什么？
 * A: Hilt 通过生成的代码直接赋值字段，private 字段无法访问。
 *
 * Q2: Fragment 用了 @AndroidEntryPoint，Activity 也要加吗？
 * A: 是的，Fragment 的宿主 Activity 必须也用 @AndroidEntryPoint。
 *
 * Q3: 什么时候用 @Provides，什么时候用 @Binds？
 * A: 接口→实现的纯绑定用 @Binds；需要构建逻辑（Builder、第三方库）用 @Provides。
 *
 * Q4: 如何在 WorkManager 中使用 Hilt？
 * A: 添加 androidx.hilt:hilt-work 依赖，Worker 用 @HiltWorker + @AssistedInject。
 *
 * Q5: Hilt 是否支持 KSP？
 * A: 从 Dagger 2.48+ 开始支持 KSP，推荐使用 KSP 替代 kapt（编译更快）。
 *
 * Q6: @Singleton 和不加作用域有什么区别？
 * A: @Singleton = 应用生命周期内只创建一个实例。
 *    不加 = 每次注入都创建新实例（可能导致状态不一致）。
 *
 * Q7: 循环依赖怎么办？
 * A: Hilt 在编译时会检测循环依赖并报错。解决方案：
 *    - 引入中间层打破循环
 *    - 使用 Lazy<T> 或 Provider<T> 延迟注入
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 十二、Hilt vs 其他 DI 方案                                                    │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * ┌──────────────┬─────────────┬──────────────┬──────────────┬────────────────┐
 * │              │ Hilt        │ Dagger       │ Koin         │ 手动 DI        │
 * ├──────────────┼─────────────┼──────────────┼──────────────┼────────────────┤
 * │ 学习成本      │ 中           │ 高           │ 低           │ 低             │
 * │ 模板代码      │ 少           │ 多           │ 少           │ 多             │
 * │ 编译时检查    │ ✅           │ ✅           │ ❌(运行时)    │ ❌             │
 * │ 性能         │ 好(编译时生成)│ 好            │ 一般(运行时反射) │ 最好        │
 * │ Android 集成 │ 原生支持     │ 需手动配置    │ 需适配       │ 需手动         │
 * │ Google 推荐  │ ✅           │ ✅           │ ❌           │ -              │
 * │ 测试支持     │ 完善          │ 完善         │ 完善          │ 取决于实现     │
 * └──────────────┴─────────────┴──────────────┴──────────────┴────────────────┘
 *
 *
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ 十三、Gradle 配置参考                                                        │
 * └──────────────────────────────────────────────────────────────────────────────┘
 *
 * // libs.versions.toml
 * [versions]
 * hilt = "2.56.2"
 * hiltNavigationCompose = "1.2.0"
 *
 * [libraries]
 * hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
 * hilt-compiler = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }
 * hilt-navigation-compose = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "hiltNavigationCompose" }
 * hilt-android-testing = { module = "com.google.dagger:hilt-android-testing", version.ref = "hilt" }
 *
 * [plugins]
 * hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
 *
 * // 项目级 build.gradle.kts
 * plugins {
 *     alias(libs.plugins.hilt) apply false
 * }
 *
 * // 模块级 build.gradle.kts
 * plugins {
 *     alias(libs.plugins.hilt)
 *     id("com.google.devtools.ksp")
 * }
 * dependencies {
 *     implementation(libs.hilt.android)
 *     ksp(libs.hilt.compiler)
 *     implementation(libs.hilt.navigation.compose)
 *
 *     // 测试
 *     androidTestImplementation(libs.hilt.android.testing)
 *     kspAndroidTest(libs.hilt.compiler)
 * }
 */
