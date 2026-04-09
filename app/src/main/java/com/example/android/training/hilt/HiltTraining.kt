package com.example.android.training.hilt

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

private const val TAG = "HiltTraining"

// ═══════════════════════════════════════════════════════════════
// 前三课回顾：
//   ① @HiltAndroidApp       ② @Inject constructor
//   ③ @Module+@Provides     ④ @AndroidEntryPoint
//   ⑤ 链式注入              ⑥ 接口+@Binds
//   ⑦ @HiltViewModel        ⑧ @Qualifier
//   ⑨ @ApplicationContext   ⑩ @Singleton vs 不加
// ═══════════════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════════════
// 第四课：2 个新知识点
//   ⑪  @ViewModelScoped —— 作用域不止 @Singleton
//   ⑫  @AssistedInject  —— 一部分参数 Hilt 注入，一部分自己传
// ═══════════════════════════════════════════════════════════════


// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// 前三课代码（保留）
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

class Greeter @Inject constructor() {
    fun sayHello(name: String): String {
        Log.d(TAG, "② Greeter.sayHello('$name')")
        return "你好, $name!"
    }
}

class Calculator {
    fun add(a: Int, b: Int): Int {
        Log.d(TAG, "③ Calculator.add($a, $b)")
        return a + b
    }
}

class MathTeacher @Inject constructor(
    private val greeter: Greeter,
    private val calculator: Calculator,
) {
    fun teach(student: String): String {
        val hello = greeter.sayHello(student)
        val result = calculator.add(3, 7)
        return "$hello\n今天学加法: 3 + 7 = $result"
    }
}

@Qualifier @Retention(AnnotationRetention.BINARY)
annotation class WeChat

@Qualifier @Retention(AnnotationRetention.BINARY)
annotation class Sms

interface MessageService {
    fun send(msg: String): String
}

class WeChatService @Inject constructor() : MessageService {
    override fun send(msg: String): String {
        Log.d(TAG, "⑧ 微信发送: $msg")
        return "微信已发送: $msg"
    }
}

class SmsService @Inject constructor() : MessageService {
    override fun send(msg: String): String {
        Log.d(TAG, "⑧ 短信发送: $msg")
        return "短信已发送: $msg"
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds @Singleton @WeChat
    abstract fun bindWeChat(impl: WeChatService): MessageService

    @Binds @Singleton @Sms
    abstract fun bindSms(impl: SmsService): MessageService
}

class AppInfo @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    fun getAppName(): String {
        val name = context.applicationInfo.loadLabel(context.packageManager).toString()
        Log.d(TAG, "⑨ AppInfo 获取应用名: $name")
        return name
    }
}

@Singleton
class SingleCounter @Inject constructor() {
    var count = 0
    fun increment(): Int {
        count++
        Log.d(TAG, "⑩ SingleCounter count=$count, 实例=${hashCode()}")
        return count
    }
}

class NewCounter @Inject constructor() {
    var count = 0
    fun increment(): Int {
        count++
        Log.d(TAG, "⑩ NewCounter count=$count, 实例=${hashCode()}")
        return count
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCalculator(): Calculator {
        Log.d(TAG, "③ AppModule: Calculator 被创建了")
        return Calculator()
    }
}

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// ⑪ @ViewModelScoped：跟 ViewModel 同生共死
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// 第三课学了 @Singleton（跟 App 同生共死）
// 但有些东西不需要全局存活，只需要跟某个 ViewModel 一样长就行
//
// @Singleton       → 整个 App 只有一个，App 死了才销毁
// @ViewModelScoped → 每个 ViewModel 一个，ViewModel 死了就销毁
// 不加             → 每次注入都是新的
//
// 注意：@ViewModelScoped 要安装到 ViewModelComponent，不是 SingletonComponent

// 购物车：每个页面（ViewModel）有自己的购物车，页面关了购物车就没了
@ViewModelScoped
class ShoppingCart @Inject constructor() {
    private val items = mutableListOf<String>()

    fun add(item: String): List<String> {
        items.add(item)
        Log.d(TAG, "⑪ ShoppingCart.add('$item'), 实例=${hashCode()}, 共${items.size}件")
        return items.toList()
    }

    fun getAll(): List<String> = items.toList()
}

// 对比：如果用 @Singleton，所有页面共享一个购物车（不合理）
// 如果不加注解，同一个 ViewModel 里注入两次就是两个不同的购物车（也不合理）
// @ViewModelScoped 刚好：同一个 ViewModel 内共享，不同 ViewModel 之间隔离

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// ⑫ @AssistedInject：一部分 Hilt 注入，一部分自己传
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// 场景：创建订单处理器，需要 Greeter（Hilt 提供）+ orderId（运行时才知道）
// 问题：orderId 是用户点击时才确定的，Hilt 编译时不知道
// 解决：@AssistedInject + @AssistedFactory

// 第一步：构造函数用 @AssistedInject，运行时参数加 @Assisted
class OrderProcessor @AssistedInject constructor(
    @Assisted private val orderId: String,   // 运行时参数 → 自己传
    private val greeter: Greeter,            // Hilt 注入 → 自动的
) {
    fun process(): String {
        val hello = greeter.sayHello("订单$orderId")
        Log.d(TAG, "⑫ OrderProcessor 处理订单: $orderId")
        return "处理中: $hello"
    }
}

// 第二步：定义工厂接口，Hilt 自动生成实现
@AssistedFactory
interface OrderProcessorFactory {
    fun create(orderId: String): OrderProcessor  // 参数对应 @Assisted 的参数
}

// 第三步：注入工厂，调用 create() 传入运行时参数（见下方 ViewModel）

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// ViewModel
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val mathTeacher: MathTeacher,
    @WeChat private val wechat: MessageService,
    @Sms private val sms: MessageService,
    private val appInfo: AppInfo,
    private val singleCounter: SingleCounter,
    private val newCounter: NewCounter,
    private val shoppingCart: ShoppingCart,          // ⑪ ViewModel 作用域
    private val orderProcessorFactory: OrderProcessorFactory,  // ⑫ 注入工厂，不是注入 OrderProcessor
) : ViewModel() {

    private val _text = MutableStateFlow("点击按钮开始第四课")
    val text: StateFlow<String> = _text

    private var orderCount = 0

    fun onWeChatClick() { _text.value = wechat.send("你好") }
    fun onSmsClick() { _text.value = sms.send("你好") }
    fun onAppInfoClick() { _text.value = "应用名: ${appInfo.getAppName()}" }
    fun onCounterClick() {
        val s = singleCounter.increment()
        val n = newCounter.increment()
        _text.value = "单例 count=$s\n非单例 count=$n"
    }

    fun onCartClick() {
        val items = shoppingCart.add("商品${shoppingCart.getAll().size + 1}")
        _text.value = "⑪ 购物车: $items\n实例=${shoppingCart.hashCode()}"
    }

    fun onOrderClick() {
        orderCount++
        // 每次点击传入不同的 orderId → 创建不同的 OrderProcessor
        val processor = orderProcessorFactory.create("ORD-$orderCount")
        _text.value = processor.process()
    }
}

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// Activity + Compose UI
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

@AndroidEntryPoint
class HiltTrainingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "④ Activity 创建")
        setContent { TrainingScreen() }
    }
}

@Composable
private fun TrainingScreen(viewModel: TrainingViewModel = hiltViewModel()) {
    val text by viewModel.text.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text)
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.onCartClick() }) {
            Text("⑪ 加购物车（多点几次，同一个实例）")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.onOrderClick() }) {
            Text("⑫ 处理订单（每次不同 orderId）")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.onWeChatClick() }) { Text("⑧ 微信") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.onSmsClick() }) { Text("⑧ 短信") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.onAppInfoClick() }) { Text("⑨ 应用名") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.onCounterClick() }) { Text("⑩ 单例vs非单例") }
    }
}

// ═══════════════════════════════════════════════════════════════
// 第四课总结
// ═══════════════════════════════════════════════════════════════
//
// ⑪ @ViewModelScoped
//    @Singleton       → 全局一个，App 活多久它活多久
//    @ViewModelScoped → 每个 ViewModel 一个，ViewModel 销毁它跟着销毁
//    不加             → 每次注入都新建
//    记住：作用域注解要和 @InstallIn 的组件对应
//      @Singleton       ↔ SingletonComponent
//      @ViewModelScoped ↔ ViewModelComponent（类上直接加就行，不需要 Module）
//
// ⑫ @AssistedInject
//    构造函数同时需要 Hilt 依赖 + 运行时参数时使用
//    三步走：
//    1. 类的构造函数用 @AssistedInject，运行时参数加 @Assisted
//    2. 定义 @AssistedFactory 接口，create() 参数对应 @Assisted
//    3. 注入工厂接口，调 create(参数) 创建实例
//
// 运行后在 Logcat 过滤 "HiltTraining" 查看日志
// ═══════════════════════════════════════════════════════════════
