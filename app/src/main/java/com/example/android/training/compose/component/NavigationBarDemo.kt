package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private data class NavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

private val navItems = listOf(
    NavItem("首页", Icons.Filled.Home, Icons.Outlined.Home),
    NavItem("搜索", Icons.Filled.Search, Icons.Outlined.Search),
    NavItem("收藏", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, badgeCount = 3),
    NavItem("消息", Icons.Filled.Email, Icons.Outlined.Email, badgeCount = 99),
    NavItem("我的", Icons.Filled.Person, Icons.Outlined.Person)
)

@Preview(showBackground = true)
@Composable
private fun PreviewNavigationBarDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionTitle("NavigationBar (底部导航栏)")
        BasicNavigationBar()

        SectionTitle("NavigationBar + Badge (带角标)")
        BadgeNavigationBar()

        SectionTitle("NavigationRail (侧边导航栏)")
        NavigationRailDemo()
    }
}

@Composable
private fun BasicNavigationBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedIndex == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = { selectedIndex = index }
            )
        }
    }
}

@Composable
private fun BadgeNavigationBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        item.badgeCount?.let { count ->
                            Badge { Text("$count") }
                        }
                    }) {
                        Icon(
                            if (selectedIndex == index) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = { selectedIndex = index }
            )
        }
    }
}

@Composable
private fun NavigationRailDemo() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationRail {
        navItems.take(4).forEachIndexed { index, item ->
            NavigationRailItem(
                icon = {
                    Icon(
                        if (selectedIndex == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = { selectedIndex = index }
            )
        }
    }
}
