package com.mnhyim.nyius.ui.feature.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mnhyim.domain.model.News
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.network.NewsRepository
import com.mnhyim.nyius.ui.navigation.Routes
import com.mnhyim.nyius.ui.util.UiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NewsViewModel(
    private val repository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val source: String = savedStateHandle.toRoute<Routes.News>().sources

    private var _news = MutableStateFlow(emptyList<News>())
    val news = _news.asStateFlow()

    private var _uiState = MutableStateFlow(UiStatus.LOADING)
    val uiState = _uiState.asStateFlow()

    init {
        getNews(source)
    }

    private fun getNews(source: String) {
        repository.getTopHeadlinesBySources(sources = source).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _uiState.value = UiStatus.SUCCESS
                    _news.value = response.data
                }

                is Resource.Error -> {
                    _uiState.value = UiStatus.ERROR
                }

                is Resource.Loading -> {
                    _uiState.value = UiStatus.LOADING
                }
            }
        }.launchIn(viewModelScope)
    }

}