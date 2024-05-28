package com.scarry.ammusicplayer.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

sealed class SpotifyPagingSource< V : Any> (
    private val loadBlock : suspend(
     limit : Int,
        offSet: Int,
        prevKey: Int?,
        nextKey : Int?,
    ) -> PagingSource.LoadResult<Int, V>
): PagingSource<Int , V >() {
    private var isLastPage : Boolean = false

    override fun getRefreshKey(state: PagingState<Int, V>): Int?  = state.anchorPosition
        ?.let{anchorPosition ->
        val anchorPage  = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val pageNumber = params.key ?: 0
        val previousKey = if (pageNumber==0 ) null else pageNumber-1
        val nextKey = if (isLastPage) null else pageNumber+1
        val loadResult = loadBlock(
            params.loadSize.coerceAtMost(50),
            params.loadSize * pageNumber,
            previousKey,
            nextKey
        )
        if (loadResult is LoadResult.Page ) isLastPage = loadResult.itemsAfter == 0
        return loadResult
    }
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}