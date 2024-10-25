package com.example.android.training.compose.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.training.compose.common.vm.HorizontalPagerVM
import com.example.core.utils.commonLog
import com.sd.lib.compose.core.rememberVMScope
import com.sd.lib.compose.refresh.FRefreshContainer
import com.sd.lib.compose.refresh.rememberFRefreshStateTop
import kotlinx.coroutines.launch

/**
 *  Pager测试
 *
 *  pagerState.animateScrollToPage(index)会使currentPage在短时间内变化两次，
 *
 *  如果用LazyRow + HorizontalPager则用pageState.scrollToPage(index)，如果用animateScrollToPage则需要对
 *  观察会使currentPage的flow做处理。
 *
 *  用Tab + HorizontalPager则可以使用animateScrollToPage，内部已经处理过了
 *
 *
 * */
@Preview
@Composable
private fun Preview() {
    TabRowTraining()
}


//region 详细使用 Tab + HorizontalPager
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabRowTraining(modifier: Modifier = Modifier) {

    val list = remember {
        List(10) { it.toString() }
    }


    val scope = rememberCoroutineScope()

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        list.size
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .collect {
                commonLog("pagerState.currentPage:${pagerState.currentPage}")
                selectedIndex = it
            }
    }
    Column(modifier = modifier.fillMaxSize()) {

        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            edgePadding = 10.dp,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedIndex])
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(color = Color.Red)
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.Red,
        ) {

            list.forEachIndexed { index, data ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = Color.Blue,
                    unselectedContentColor = Color.Yellow,
                ) {

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = data,
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }

        PagerContent(
            list = list,
            pagerState = pagerState,
        )

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerContent(
    list: List<String>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val vmScope = rememberVMScope<HorizontalPagerVM>()

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->

        list.getOrNull(page)?.let { id ->
            val vm = vmScope.get(key = id).apply {
                init(id.toInt())
            }

            val data = vm.pager.collectAsLazyPagingItems()


            PullToRefresh(
                isRefresh = data.loadState.refresh == LoadState.Loading,
                onRefresh = { data.refresh() },
            ) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                ) {
                    items(count = data.itemCount) { index ->
                        val text = data[index]!!

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = text,
                                fontSize = 30.sp
                            )
                        }

                    }
                }
            }

        }


    }
}

@Composable
private fun PullToRefresh(
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = rememberFRefreshStateTop {
        onRefresh()
    }

    Box(
        modifier = modifier
            .nestedScroll(state.nestedScrollConnection)
            .clipToBounds(),
    ) {
        content()
        FRefreshContainer(
            state = state,
            isRefreshing = isRefresh,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}

//endregion

//region 简单Pager Tab + HorizontalPager
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SimpleTabRowTraining(modifier: Modifier = Modifier) {

    val list = remember {
        List(10) { it.toString() }
    }

    val scope = rememberCoroutineScope()

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        list.size
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .collect {
                commonLog("pagerState.currentPage:${pagerState.currentPage}")
                selectedIndex = it
            }
    }

    Column(modifier = modifier.fillMaxSize()) {

        ScrollableTabRow(selectedTabIndex = selectedIndex) {

            list.forEachIndexed { index, data ->
                Tab(selected = selectedIndex == index, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }) {

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = index.toString(),
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }

        HorizontalPager(state = pagerState) { page ->

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = page.toString(),
                    fontSize = 30.sp
                )
            }
        }

    }


}

//endregion

//region 简单pager LazyRow + HorizontalPager
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SimpleHorizontalPagerTraining(modifier: Modifier = Modifier) {

    val list = remember {
        List(10) { it.toString() }
    }

    val pageState = rememberPagerState {
        list.size
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val rowState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(pageState) {
        snapshotFlow { pageState.currentPage }
            .collect {
                commonLog("currentPage:$it")
                selectedIndex = it
                rowState.animateScrollToItem(((it - 2).coerceAtLeast(0)))
            }
    }

    Column {

        LazyRow(state = rowState) {

            itemsIndexed(list) { index, data ->
                Box(modifier = Modifier
                    .background(if (selectedIndex == index) Color.Gray else Color.White)
                    .padding(vertical = 10.dp, horizontal = 30.dp)
                    .clickable {
                        commonLog("clickIndex:$index")
                        scope.launch {
                            pageState.scrollToPage(index)
                        }
                    }) {
                    Text(
                        text = data,
                        fontSize = 20.sp,
                        modifier = Modifier
                    )
                }

            }

        }

        HorizontalPager(state = pageState, modifier = Modifier.fillMaxSize()) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = page.toString(), modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

    }

}
//endregion




