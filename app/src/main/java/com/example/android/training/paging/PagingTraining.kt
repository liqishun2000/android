package com.example.android.training.paging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
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


    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Button(onClick = { list.refresh() }) {
                Text(text = "刷新")
            }


        }

        PullToRefresh(
            modifier = Modifier.background(color = Color.Gray),
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


private const val PAGE_SIZE = 20


private val pager = Pager(
    config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE),
    pagingSourceFactory = { DataSource() }
)

/**
 *  第一次请求或者手动刷新 loadSize 为 pageSize*3
 *
 *  手动刷新，如果请求的数据量不满足loadSize会一直滚动到第一页(bug?)
 *
 *
 * */
private class DataSource : PagingSource<Int, String>() {


    //刷新后的anchor,用来定位分页 null默认为第一页
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        commonLog("getRefreshKey->anchorPosition:${state.anchorPosition}")
//        return state.anchorPosition?.let { anchorPosition ->
//            val closestPageToPosition = state.closestPageToPosition(anchorPosition)
//            commonLog("getRefreshKey->closestPageToPosition:$closestPageToPosition")
//            closestPageToPosition?.prevKey?.plus(1) ?: closestPageToPosition?.nextKey?.minus(1)
//        }
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val page = params.key ?: 1

            //注意loadSize,默认为三倍PageSize
            val loadSize = params.loadSize
            val items = getList(page, loadSize)


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

private suspend fun getList(page: Int, loadSize: Int): List<String> {
    delay(100)
    commonLog("getList->page:$page,loadSize:$loadSize")
    if (page > 5) {
        return emptyList()
    }

    return List(loadSize) { "$page: $it" }
}

