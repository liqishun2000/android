package com.example.android.training.compose.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.utils.commonLog
import kotlinx.coroutines.launch

@Preview
@Composable
private fun Preview() {
    SimpleHorizontalPagerTraining()
}


//region Tab + HorizontalPager


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




