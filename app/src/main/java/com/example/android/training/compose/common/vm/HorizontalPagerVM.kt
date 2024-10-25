package com.example.android.training.compose.common.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.example.android.ui.viewmodel.base.BaseViewModel

class HorizontalPagerVM : BaseViewModel<HorizontalPagerVM.State, Unit>(State()) {

    private var id:Int = -1

    val pager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { DataSource(id) }
    ).flow.cachedIn(viewModelScope)

    fun init(id:Int){
        this.id = id
    }

    data class State(
        val show: Boolean = true
    )
}

class DataSource(private val id:Int) : PagingSource<Int, String>() {
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val page = params.key ?: 1
            val loadSize = params.loadSize
            val items = getList(id,page, loadSize)

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

private fun getList(id:Int,page: Int, loadSize: Int): List<String> {
    if (page > 3) return emptyList()
    return List(loadSize) { "id:$id,value:$it" }
}