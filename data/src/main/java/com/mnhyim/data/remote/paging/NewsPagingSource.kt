package com.mnhyim.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mnhyim.data.dto.NewsDto
import com.mnhyim.data.remote.NewsApi

class NewsPagingSource(
    private val api: NewsApi,
    private val sources: String
) : PagingSource<Int, NewsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsDto> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = api.getTopHeadlinesBySources(sources, page)
            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.d("Paging", "${e.localizedMessage}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}