package com.example.android.training.compose.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.utils.commonLog
import com.sd.lib.compose.refresh.FRefreshContainer
import com.sd.lib.compose.refresh.rememberFRefreshStateTop
import kotlinx.coroutines.delay

/**
 * 分页列表
 *
 * */
@Preview
@Composable
private fun Preview() {
    PagingTraining(list = pager.flow.collectAsLazyPagingItems())
}

@Composable
private fun PagingTraining(
    list: LazyPagingItems<String>,
    modifier: Modifier = Modifier,
) {
    SideEffect {
        commonLog("size:${list.itemCount} loadState:${list.loadState}")
    }



    Column(modifier = modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {


        }

        PullToRefresh(
            isRefresh = list.loadState.refresh == LoadState.Loading,
            onRefresh = {
                commonLog("开始刷新")
                list.refresh()
            },
        ) {
            LazyColumn(Modifier.fillMaxSize()) {

                items(count = list.itemCount) { index ->
                    val data = list[index]
                    Text(
                        text = "$data",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        textAlign = TextAlign.Center
                    )

                }
            }

            //没有数据时的UI
            if (list.itemCount == 0) {
                if (list.loadState.refresh == LoadState.Loading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "暂无数据",
                            fontSize = 40.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
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
            .clipToBounds()
    ) {
        content()

        FRefreshContainer(
            state = state,
            isRefreshing = isRefresh,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }

}


private val pager = Pager(
    config = PagingConfig(20),
    pagingSourceFactory = { DataSource() }
)

private class DataSource : PagingSource<Int, String>() {
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val items = getList(page, pageSize)


            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

private suspend fun getList(page: Int, pageSize: Int): List<String> {
    delay(2000)
    commonLog("getList->page:$page,pageSize:$pageSize")
    if (page > 3) {
        return emptyList()
    }

    return List(pageSize) { it.toString() }
}