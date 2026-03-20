package com.example.android.training.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun PreviewTabDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionTitle("基本 TabRow")
        BasicTabDemo()

        SectionTitle("带图标 TabRow")
        IconTabDemo()

        SectionTitle("ScrollableTabRow (可滚动)")
        ScrollableTabDemo()

        SectionTitle("TabRow + HorizontalPager")
        TabWithPagerDemo()
    }
}

@Composable
private fun BasicTabDemo() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("推荐", "关注", "热门")

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(title) }
            )
        }
    }
}

@Composable
private fun IconTabDemo() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("首页" to Icons.Default.Home, "收藏" to Icons.Default.Favorite, "我的" to Icons.Default.Person)

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, (title, icon) ->
            LeadingIconTab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(title) },
                icon = { Icon(icon, contentDescription = title) }
            )
        }
    }
}

@Composable
private fun ScrollableTabDemo() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("全部", "科技", "体育", "娱乐", "财经", "教育", "健康", "旅游", "美食")

    ScrollableTabRow(selectedTabIndex = selectedTab, edgePadding = 16.dp) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(title) }
            )
        }
    }
}

@Composable
private fun TabWithPagerDemo() {
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("${tabs[page]} 的内容", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
