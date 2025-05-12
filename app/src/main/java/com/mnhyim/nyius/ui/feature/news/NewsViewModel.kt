package com.mnhyim.nyius.ui.feature.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.network.NewsRepository
import com.mnhyim.nyius.ui.navigation.Routes
import com.mnhyim.nyius.ui.util.UiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class NewsViewModel(
    private val repository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val source: String = savedStateHandle.toRoute<Routes.News>().sources
    private val sourceName: String = savedStateHandle.toRoute<Routes.News>().sourcesName
    private val category: Category =
        Category.valueOf(savedStateHandle.toRoute<Routes.News>().category.uppercase())

    private var _uiState = MutableStateFlow(NewsUiState(source = sourceName, category = category))
    val uiState = _uiState.asStateFlow()

    init {
        getNews(source)
    }

    private fun getNews(source: String) {
        repository.getTopHeadlinesBySources(sources = source).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            status = UiStatus.SUCCESS,
                            news = response.data,
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(status = UiStatus.ERROR)
                    }
                }

                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(status = UiStatus.LOADING)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}