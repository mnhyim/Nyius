package com.mnhyim.nyius.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mnhyim.data.remote.paging.PagedNewsRepository
import com.mnhyim.domain.model.News
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val pagedRepository: PagedNewsRepository,
) : ViewModel() {

    fun searchPagedNews(query: String): Flow<PagingData<News>> {
        return pagedRepository.searchNews(query).cachedIn(viewModelScope)
    }
}