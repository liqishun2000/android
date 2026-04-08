package com.example.android.training.hilt

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

// ========================================================================================
// build.gradle.kts 配置（项目级）
// ========================================================================================
// plugins {
//     id("com.google.dagger.hilt.android") version "2.56.2" apply false
// }

// ========================================================================================
// build.gradle.kts 配置（模块级）
// ========================================================================================
// plugins {
//     id("com.google.dagger.hilt.android")
//     id("com.google.devtools.ksp")
// }
// dependencies {
//     implementation("com.google.dagger:hilt-android:2.56.2")
//     ksp("com.google.dagger:hilt-compiler:2.56.2")
//
//     // ViewModel 集成（如果使用 HiltViewModel）
//     implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
// }

// ========================================================================================
// 1. Application —— 必须用 @HiltAndroidApp 标注
// ========================================================================================

/**
 * Hilt 的入口点，必须在 Application 上添加 @HiltAndroidApp。
 * 它会触发 Hilt 代码生成，生成整个应用的依赖注入容器（SingletonComponent）。
 */
//@HiltAndroidApp   // 实际使用时取消注释
class HiltDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

// ========================================================================================
// 2. 数据模型
// ========================================================================================

data class User(val id: String, val name: String, val email: String)

// ========================================================================================
// 3. 接口定义 —— 面向接口编程，便于测试和替换实现
// ========================================================================================

/**
 * 用户数据仓库接口
 */
interface UserRepository {
    suspend fun getUser(id: String): User
    suspend fun getAllUsers(): List<User>
}

/**
 * 日志接口，演示 @Qualifier 区分多个实现
 */
interface Logger {
    fun log(tag: String, message: String)
}

// ========================================================================================
// 4. 接口实现 —— 使用 @Inject constructor 让 Hilt 知道如何创建实例
// ========================================================================================

/**
 * UserRepository 的远程实现。
 *
 * @Inject constructor 告诉 Hilt：当需要 UserRepositoryImpl 时，调用这个构造函数来创建。
 * 构造函数参数也会被 Hilt 自动注入。
 */
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,   // Hilt 会自动提供
    private val logger: Logger            // 通过 @Binds 或 @Qualifier 提供
) : UserRepository {

    override suspend fun getUser(id: String): User {
        logger.log("UserRepo", "获取用户: $id")
        return apiService.fetchUser(id)
    }

    override suspend fun getAllUsers(): List<User> {
        logger.log("UserRepo", "获取所有用户")
        return apiService.fetchAllUsers()
    }
}

/**
 * 本地缓存实现，演示同一接口的不同实现
 */
class LocalUserRepository @Inject constructor(
    @ApplicationContext private val context: Context   // @ApplicationContext 注入应用级 Context
) : UserRepository {

    override suspend fun getUser(id: String): User {
        return User(id, "本地用户", "local@example.com")
    }

    override suspend fun getAllUsers(): List<User> {
        return listOf(User("1", "本地用户1", "local1@example.com"))
    }
}

// ========================================================================================
// 5. 第三方类 / 无法修改构造函数的类 —— 需要通过 @Module + @Provides 提供
// ========================================================================================

/**
 * 模拟 API 服务（类似 Retrofit 创建的接口实例）
 */
class ApiService {
    fun fetchUser(id: String): User = User(id, "远程用户", "remote@example.com")
    fun fetchAllUsers(): List<User> = listOf(
        User("1", "张三", "zhangsan@example.com"),
        User("2", "李四", "lisi@example.com")
    )
}

/**
 * 控制台日志实现
 */
class ConsoleLogger @Inject constructor() : Logger {
    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}

/**
 * 文件日志实现
 */
class FileLogger @Inject constructor(
    @ApplicationContext private val context: Context
) : Logger {
    override fun log(tag: String, message: String) {
        // 实际场景写入文件
        Log.i(tag, "[FILE] $message")
    }
}

// ========================================================================================
// 6. @Qualifier —— 当同一接口有多个实现时，用 Qualifier 区分
// ========================================================================================

/**
 * 自定义限定符注解，用于区分同一类型的不同实现。
 *
 * 使用场景：UserRepository 有远程和本地两种实现，
 * 注入时需要明确指定要哪个。
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConsoleLog

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FileLog

// ========================================================================================
// 7. @Module + @InstallIn —— 告诉 Hilt 如何提供依赖
// ========================================================================================

/**
 * 使用 @Provides 提供依赖。
 *
 * 适用场景：
 * - 第三方库的类（无法修改构造函数）
 * - 需要配置后才能创建的实例（如 Retrofit、OkHttpClient）
 * - 接口实例需要通过 Builder 模式创建
 *
 * @InstallIn(SingletonComponent::class) 表示这些依赖的生命周期跟随 Application。
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * @Provides 告诉 Hilt 如何创建 ApiService 实例。
     * @Singleton 确保整个 App 生命周期只创建一个实例。
     */
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        // 实际项目中这里通常是 Retrofit.create(ApiService::class.java)
        return ApiService()
    }
}

/**
 * 使用 @Binds 绑定接口和实现。
 *
 * 适用场景：
 * - 接口与实现类的绑定（实现类已有 @Inject constructor）
 * - 比 @Provides 更简洁高效（不会生成额外的工厂类）
 *
 * 注意：@Binds 方法必须是抽象的，所以 Module 要用 abstract class。
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * @Binds 将 UserRepositoryImpl 绑定到 UserRepository 接口。
     * 当注入 @RemoteRepository UserRepository 时，Hilt 会提供 UserRepositoryImpl。
     */
    @Binds
    @Singleton
    @RemoteRepository
    abstract fun bindRemoteRepository(impl: UserRepositoryImpl): UserRepository

    /**
     * 绑定本地实现到 @LocalRepository 限定的 UserRepository
     */
    @Binds
    @Singleton
    @LocalRepository
    abstract fun bindLocalRepository(impl: LocalUserRepository): UserRepository
}

/**
 * Logger 绑定模块
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {

    @Binds
    @Singleton
    @ConsoleLog
    abstract fun bindConsoleLogger(impl: ConsoleLogger): Logger

    @Binds
    @Singleton
    @FileLog
    abstract fun bindFileLogger(impl: FileLogger): Logger

    /**
     * 不使用 Qualifier 的默认绑定。
     * 当注入 Logger（不带 Qualifier）时，默认使用 ConsoleLogger。
     */
    @Binds
    @Singleton
    abstract fun bindDefaultLogger(impl: ConsoleLogger): Logger
}

// ========================================================================================
// 8. @HiltViewModel —— ViewModel 注入
// ========================================================================================

/**
 * 使用 @HiltViewModel 标注的 ViewModel 可以：
 * - 通过 @Inject constructor 接收依赖
 * - 在 Activity/Fragment 中通过 hiltViewModel() 获取
 * - SavedStateHandle 也可以直接注入
 *
 * Hilt 会自动为它创建 ViewModelProvider.Factory。
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    @RemoteRepository private val userRepository: UserRepository,   // 注入远程仓库
    @ConsoleLog private val logger: Logger                          // 注入控制台日志
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun loadUsers() {
        viewModelScope.launch {
            logger.log("UserVM", "开始加载用户列表")
            _users.value = userRepository.getAllUsers()
        }
    }

    fun loadUser(id: String) {
        viewModelScope.launch {
            _currentUser.value = userRepository.getUser(id)
        }
    }
}

// ========================================================================================
// 9. @AndroidEntryPoint —— 在 Activity / Fragment 中启用注入
// ========================================================================================

/**
 * @AndroidEntryPoint 使这个 Activity 成为 Hilt 的注入目标。
 *
 * 支持的 Android 类：
 * - Activity
 * - Fragment
 * - View
 * - Service
 * - BroadcastReceiver
 *
 * 注入方式：
 * - 字段注入：@Inject lateinit var xxx
 * - ViewModel：通过 hiltViewModel()（Compose）或 by viewModels()（传统方式）
 */
//@AndroidEntryPoint   // 实际使用时取消注释
class HiltDemoActivity : ComponentActivity() {

    /**
     * 字段注入：Hilt 会在 onCreate 之前自动赋值。
     * 注意：字段不能是 private 的。
     */
    @Inject
    @ConsoleLog
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logger.log("HiltDemo", "Activity 已创建，Logger 通过字段注入获得")

        setContent {
            UserScreen()
        }
    }
}

/**
 * Compose 中使用 hiltViewModel() 获取注入了依赖的 ViewModel
 */
@Composable
private fun UserScreen(
    // hiltViewModel() 需要 androidx.hilt:hilt-navigation-compose 依赖
    // viewModel: UserViewModel = hiltViewModel()
) {
    // val users by viewModel.users.collectAsState()
    // val currentUser by viewModel.currentUser.collectAsState()

    Text("Hilt Demo - 查看代码注释了解用法")
}

// ========================================================================================
// 10. Scoping（作用域）—— 控制依赖的生命周期
// ========================================================================================

/**
 * Hilt 组件层级与对应的作用域注解：
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Component                   │ Scope              │ 生命周期     │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ SingletonComponent          │ @Singleton          │ Application │
 * │ ActivityRetainedComponent   │ @ActivityRetainedScoped│ ViewModel│
 * │ ViewModelComponent          │ @ViewModelScoped    │ ViewModel   │
 * │ ActivityComponent           │ @ActivityScoped     │ Activity    │
 * │ FragmentComponent           │ @FragmentScoped     │ Fragment    │
 * │ ViewComponent               │ @ViewScoped         │ View        │
 * │ ServiceComponent            │ @ServiceScoped      │ Service     │
 * └─────────────────────────────────────────────────────────────────┘
 *
 * 不加作用域注解：每次注入都会创建新实例（无作用域 = 每次 new）。
 * 加了作用域注解：在对应组件的生命周期内只创建一次（单例行为）。
 */

// ========================================================================================
// 11. Assisted Injection（辅助注入）—— 运行时参数 + 依赖注入混合
// ========================================================================================

/**
 * 当一个类的构造函数中既有需要 Hilt 注入的依赖，又有运行时才知道的参数时，
 * 使用 Assisted Injection。
 *
 * 典型场景：创建对象时需要传入 userId、config 等运行时参数。
 */
class UserDetailProcessor @AssistedInject constructor(
    @Assisted private val userId: String,            // 运行时参数，用 @Assisted 标注
    @RemoteRepository private val repository: UserRepository,  // Hilt 注入
    @ConsoleLog private val logger: Logger                     // Hilt 注入
) {
    suspend fun process(): User {
        logger.log("Processor", "处理用户详情: $userId")
        return repository.getUser(userId)
    }
}

/**
 * 辅助注入需要定义一个工厂接口，用 @AssistedFactory 标注。
 * Hilt 会自动生成该接口的实现。
 */
@AssistedFactory
interface UserDetailProcessorFactory {
    fun create(userId: String): UserDetailProcessor
}

/**
 * 在 ViewModel 中使用辅助注入工厂
 */
@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val processorFactory: UserDetailProcessorFactory
) : ViewModel() {

    fun processUser(userId: String) {
        val processor = processorFactory.create(userId)
        viewModelScope.launch {
            val user = processor.process()
            Log.d("UserDetail", "处理完成: ${user.name}")
        }
    }
}

// ========================================================================================
// 12. @EntryPoint —— 在 Hilt 不支持的类中获取依赖
// ========================================================================================

/**
 * 某些类不受 Hilt 管理（如 ContentProvider、第三方库回调），
 * 可以通过 @EntryPoint 接口来获取依赖。
 *
 * 使用步骤：
 * 1. 定义 @EntryPoint 接口，声明需要的依赖
 * 2. 通过 EntryPointAccessors 获取实例
 */
//@EntryPoint
//@InstallIn(SingletonComponent::class)
//interface MyEntryPoint {
//    fun getUserRepository(): @RemoteRepository UserRepository
//    fun getLogger(): @ConsoleLog Logger
//}

// 在不受 Hilt 管理的类中使用：
// val entryPoint = EntryPointAccessors.fromApplication(context, MyEntryPoint::class.java)
// val repository = entryPoint.getUserRepository()

// ========================================================================================
// 13. 多模块项目中的 Hilt
// ========================================================================================

/**
 * 在多模块项目中使用 Hilt：
 *
 * 1. 每个模块都需要添加 Hilt 依赖和 KSP 插件
 * 2. @Module 可以定义在任何模块中，只要 @InstallIn 正确即可
 * 3. app 模块的 Application 必须添加 @HiltAndroidApp
 * 4. 各模块的 @Module 会在编译时自动合并到对应的 Component 中
 *
 * 注意：如果使用 Dynamic Feature Module，需要额外配置。
 */

// ========================================================================================
// 14. 测试中的 Hilt
// ========================================================================================

/**
 * Hilt 支持在测试中替换依赖：
 *
 * 1. 单元测试 —— 不需要 Hilt，直接 mock 依赖传入构造函数
 *    val repo = mockk<UserRepository>()
 *    val vm = UserViewModel(repo, mockk())
 *
 * 2. UI 测试 / 集成测试 —— 使用 @HiltAndroidTest
 *    @HiltAndroidTest
 *    class MyActivityTest {
 *        @get:Rule
 *        var hiltRule = HiltAndroidRule(this)
 *
 *        @BindValue   // 替换真实依赖
 *        val fakeRepo: UserRepository = FakeUserRepository()
 *    }
 *
 * 3. 替换整个 Module —— 使用 @TestInstallIn 或 @UninstallModules
 *    @TestInstallIn(
 *        components = [SingletonComponent::class],
 *        replaces = [RepositoryModule::class]   // 替换生产环境的 Module
 *    )
 *    @Module
 *    abstract class FakeRepositoryModule {
 *        @Binds abstract fun bindRepo(fake: FakeUserRepository): UserRepository
 *    }
 */
