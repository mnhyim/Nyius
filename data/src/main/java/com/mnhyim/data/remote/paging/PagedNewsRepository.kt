package com.mnhyim.data.remote.paging

import androidx.paging.PagingData
import com.mnhyim.domain.model.News
import kotlinx.coroutines.flow.Flow

interface PagedNewsRepository {
    fun getPagedNews(source: String): Flow<PagingData<News>>
    fun searchNews(q: String, page: Int = 1): Flow<PagingData<News>>
}