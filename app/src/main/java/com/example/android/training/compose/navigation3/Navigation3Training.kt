package com.example.android.training.compose.navigation3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable


/**
 * ==================== Navigation 3 核心概念 ====================
 *
 * Navigation 3 是全新的 Compose 导航库，与 Navigation 2 完全不同：
 *
 * 1. 用户自主管理回退栈（backStack）—— 不再有隐藏的 NavController
 *    backStack 就是一个普通的 SnapshotStateList<Any>，你可以随意操作它
 *
 * 2. 类型安全路由 —— 使用 @Serializable data class / data object 定义路由
 *    不再使用字符串路由，编译期就能发现参数错误
 *
 * 3. NavDisplay + entryProvider 模式 —— 替代旧的 NavHost + composable() DSL
 *    NavDisplay 负责渲染回退栈顶部的内容
 *    entryProvider 负责把路由 Key 解析为对应的 NavEntry（包含 UI 内容）
 *
 * 4. 完全开放的 API —— 一切可定制，没有黑盒
 *
 * 核心依赖：
 *   androidx.navigation3:navigation3-runtime  → NavEntry, entryProvider DSL
 *   androidx.navigation3:navigation3-ui       → NavDisplay, Scene
 *
 * 要求：compileSdk >= 36
 */


// ==================== 路由定义（类型安全） ====================

/**
 * 使用 @Serializable 注解定义路由 Key。
 * data object 用于无参数的页面，data class 用于有参数的页面。
 */

@Serializable
data object HomeKey

@Serializable
data object ListKey

@Serializable
data class DetailKey(val itemId: Int, val itemTitle: String)

@Serializable
data object SettingsKey

@Serializable
data object ProfileKey

@Serializable
data class UserKey(val userId: String, val userName: String)


// ==================== Demo 1: 基础导航（前进/后退/传参） ====================

@Preview(showBackground = true)
@Composable
fun BasicNavigationDemo() {
    /**
     * 核心：backStack 是一个 mutableStateListOf，你完全拥有它。
     * - backStack.add(key) → 前进到新页面
     * - backStack.removeLastOrNull() → 返回上一页
     * - backStack.clear() 然后 add → 重置导航
     *
     * 初始化时放入首页的 Key，这样 App 启动后就会显示首页。
     */
    val backStack = remember { mutableStateListOf<Any>(HomeKey) }

    NavDisplay(
        backStack = backStack,
        /**
         * onBack: 处理系统返回键。当用户按下系统返回按钮时触发。
         * 通常在这里执行 backStack.removeLastOrNull() 弹出栈顶页面。
         */
        onBack = { backStack.removeLastOrNull() },
        /**
         * entryProvider 是一个 DSL，用于将 Key 映射到 NavEntry。
         * entry<KeyType> { key -> ... } 会匹配对应类型的 Key，
         * 并返回一个 NavEntry 包含要渲染的 Composable 内容。
         */
        entryProvider = entryProvider {

            // 首页
            entry<HomeKey> {
                HomeScreen(
                    onNavigateToList = {
                        backStack.add(ListKey)
                    },
                    onNavigateToSettings = {
                        backStack.add(SettingsKey)
                    },
                    onNavigateToProfile = {
                        backStack.add(ProfileKey)
                    }
                )
            }

            // 列表页
            entry<ListKey> {
                ListScreen(
                    onItemClick = { id, title ->
                        // 传参导航：直接构造带参数的 data class
                        backStack.add(DetailKey(itemId = id, itemTitle = title))
                    },
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            // 详情页 —— 接收参数
            entry<DetailKey> { key ->
                // key 就是 DetailKey 实例，直接访问其属性
                DetailScreen(
                    itemId = key.itemId,
                    itemTitle = key.itemTitle,
                    onNavigateToUser = { userId, userName ->
                        backStack.add(UserKey(userId = userId, userName = userName))
                    },
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            // 设置页
            entry<SettingsKey> {
                SettingsScreen(
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            // 个人主页
            entry<ProfileKey> {
                ProfileScreen(
                    onNavigateToUser = { userId, userName ->
                        backStack.add(UserKey(userId = userId, userName = userName))
                    },
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            // 用户详情页 —— 多参数传递
            entry<UserKey> { key ->
                UserScreen(
                    userId = key.userId,
                    userName = key.userName,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onBackToHome = {
                        // 回退栈操作：清空后只保留首页，实现"回到首页"
                        backStack.clear()
                        backStack.add(HomeKey)
                    }
                )
            }
        }
    )
}


// ==================== Demo 2: 底部导航栏 + 多 Tab 独立回退栈 ====================

private enum class BottomTab(val label: String, val icon: ImageVector) {
    HOME("首页", Icons.Default.Home),
    FAVORITE("收藏", Icons.Default.Favorite),
    PROFILE("我的", Icons.Default.Person),
}

@Preview(showBackground = true)
@Composable
fun BottomNavDemo() {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    /**
     * 每个 Tab 维护自己独立的回退栈，切换 Tab 时不会丢失其他 Tab 的状态。
     * 这是 Navigation 3 的优势 —— 回退栈完全由你管理，
     * 多个回退栈可以轻松并存。
     */
    val tabs = BottomTab.entries
    val tabBackStacks = remember {
        tabs.map { tab ->
            mutableStateListOf<Any>(
                when (tab) {
                    BottomTab.HOME -> HomeKey
                    BottomTab.FAVORITE -> ListKey
                    BottomTab.PROFILE -> ProfileKey
                }
            )
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            if (selectedTab == index) {
                                // 已选中的 Tab 再次点击：回到 Tab 根页面
                                val stack = tabBackStacks[index]
                                if (stack.size > 1) {
                                    val root = stack.first()
                                    stack.clear()
                                    stack.add(root)
                                }
                            } else {
                                selectedTab = index
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // 根据当前选中 Tab 显示对应的 NavDisplay
            val currentBackStack = tabBackStacks[selectedTab]

            NavDisplay(
                backStack = currentBackStack,
                onBack = { currentBackStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<HomeKey> {
                        HomeScreen(
                            onNavigateToList = { currentBackStack.add(ListKey) },
                            onNavigateToSettings = { currentBackStack.add(SettingsKey) },
                            onNavigateToProfile = { currentBackStack.add(ProfileKey) }
                        )
                    }
                    entry<ListKey> {
                        ListScreen(
                            onItemClick = { id, title ->
                                currentBackStack.add(DetailKey(id, title))
                            },
                            onBack = { currentBackStack.removeLastOrNull() }
                        )
                    }
                    entry<DetailKey> { key ->
                        DetailScreen(
                            itemId = key.itemId,
                            itemTitle = key.itemTitle,
                            onNavigateToUser = { uid, name ->
                                currentBackStack.add(UserKey(uid, name))
                            },
                            onBack = { currentBackStack.removeLastOrNull() }
                        )
                    }
                    entry<SettingsKey> {
                        SettingsScreen(
                            onBack = { currentBackStack.removeLastOrNull() }
                        )
                    }
                    entry<ProfileKey> {
                        ProfileScreen(
                            onNavigateToUser = { uid, name ->
                                currentBackStack.add(UserKey(uid, name))
                            },
                            onBack = { currentBackStack.removeLastOrNull() }
                        )
                    }
                    entry<UserKey> { key ->
                        UserScreen(
                            userId = key.userId,
                            userName = key.userName,
                            onBack = { currentBackStack.removeLastOrNull() },
                            onBackToHome = {
                                val root = currentBackStack.first()
                                currentBackStack.clear()
                                currentBackStack.add(root)
                            }
                        )
                    }
                }
            )
        }
    }
}


// ==================== Demo 3: 回退栈高级操作 ====================

@Preview(showBackground = true)
@Composable
fun BackStackManipulationDemo() {
    val backStack = remember { mutableStateListOf<Any>(HomeKey) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部显示当前回退栈状态
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    "当前回退栈 (${backStack.size} 个页面):",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    backStack.joinToString(" → ") { it.keyName() },
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 操作按钮区
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedButton(
                onClick = { backStack.add(ListKey) },
                modifier = Modifier.weight(1f)
            ) { Text("Push", fontSize = 11.sp) }

            OutlinedButton(
                onClick = { backStack.removeLastOrNull() },
                modifier = Modifier.weight(1f),
                enabled = backStack.size > 1
            ) { Text("Pop", fontSize = 11.sp) }

            OutlinedButton(
                onClick = {
                    // popTo: 弹出到目标页面（保留目标页面）
                    val targetIndex = backStack.indexOfLast { it is HomeKey }
                    if (targetIndex >= 0) {
                        while (backStack.size > targetIndex + 1) {
                            backStack.removeLastOrNull()
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = backStack.size > 1
            ) { Text("PopTo首页", fontSize = 11.sp) }

            OutlinedButton(
                onClick = {
                    // 替换栈顶
                    if (backStack.isNotEmpty()) {
                        backStack[backStack.lastIndex] = SettingsKey
                    }
                },
                modifier = Modifier.weight(1f)
            ) { Text("Replace", fontSize = 11.sp) }
        }

        // NavDisplay 渲染区域
        Box(modifier = Modifier.weight(1f)) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<HomeKey> {
                        SimplePageContent(
                            title = "首页",
                            color = Color(0xFF6200EE),
                            description = "这是首页，使用上方按钮操作回退栈"
                        )
                    }
                    entry<ListKey> {
                        SimplePageContent(
                            title = "列表页",
                            color = Color(0xFF03DAC5),
                            description = "通过 Push 添加到回退栈"
                        )
                    }
                    entry<SettingsKey> {
                        SimplePageContent(
                            title = "设置页",
                            color = Color(0xFFFF6D00),
                            description = "通过 Replace 替换栈顶得到"
                        )
                    }
                    entry<DetailKey> { key ->
                        SimplePageContent(
                            title = "详情 #${key.itemId}",
                            color = Color(0xFFBB86FC),
                            description = key.itemTitle
                        )
                    }
                    entry<ProfileKey> {
                        SimplePageContent(
                            title = "个人页",
                            color = Color(0xFFCF6679),
                            description = "个人主页"
                        )
                    }
                    entry<UserKey> { key ->
                        SimplePageContent(
                            title = "用户: ${key.userName}",
                            color = Color(0xFF018786),
                            description = "用户ID: ${key.userId}"
                        )
                    }
                }
            )
        }
    }
}


// ==================== 各页面 UI 实现 ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    onNavigateToList: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Navigation 3 Demo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Navigation 3 核心特点",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "• 用户自主管理 backStack\n• 类型安全路由 (@Serializable)\n• NavDisplay + entryProvider 模式\n• 完全开放可定制",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateToList,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Home, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("进入列表页（传参示例）")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onNavigateToSettings,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("进入设置页")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onNavigateToProfile,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Person, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("进入个人主页")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    onItemClick: (Int, String) -> Unit,
    onBack: () -> Unit,
) {
    val items = remember {
        (1..20).map { "列表项 #$it - ${listOf("Kotlin", "Compose", "Navigation 3", "Material 3", "Coroutines").random()}" }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("列表页") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(items) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                        .clickable {
                            // 点击时把 index 和 title 作为参数传递给详情页
                            onItemClick(index, item)
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${index + 1}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(item, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreen(
    itemId: Int,
    itemTitle: String,
    onNavigateToUser: (String, String) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("详情 #$itemId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("接收到的参数：", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("itemId = $itemId", fontSize = 14.sp)
                    Text("itemTitle = $itemTitle", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Navigation 3 使用 @Serializable data class 传参，\n"
                                + "直接在路由 Key 中定义参数，类型安全！",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onNavigateToUser("user_$itemId", "用户${itemId + 1}号") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("查看关联用户（多级导航）")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("设置页面", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "这是一个无参数页面\n使用 data object 定义路由 Key",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    onNavigateToUser: (String, String) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("个人主页") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("张三", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("zhangsan@example.com", color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(24.dp))

            val friends = listOf("李四" to "user_001", "王五" to "user_002", "赵六" to "user_003")
            Text("好友列表：", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            friends.forEach { (name, id) ->
                OutlinedButton(
                    onClick = { onNavigateToUser(id, name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text("查看 $name 的主页")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserScreen(
    userId: String,
    userName: String,
    onBack: () -> Unit,
    onBackToHome: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(userName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            userName.first().toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "ID: $userId",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "通过 data class 路由传递了 userId 和 userName 两个参数",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("返回上一页 (removeLastOrNull)")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onBackToHome,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("回到首页 (clear + add)")
            }
        }
    }
}

@Composable
private fun SimplePageContent(
    title: String,
    color: Color,
    description: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        title.first().toString(),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    description,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}


// ==================== 工具方法 ====================

private fun Any.keyName(): String = when (this) {
    is HomeKey -> "首页"
    is ListKey -> "列表"
    is DetailKey -> "详情#$itemId"
    is SettingsKey -> "设置"
    is ProfileKey -> "个人"
    is UserKey -> userName
    else -> toString()
}
