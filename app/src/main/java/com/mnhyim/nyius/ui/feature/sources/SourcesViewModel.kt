package com.mnhyim.nyius.ui.feature.sources

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

class SourcesViewModel(
    private val repository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val category: Category =
        Category.valueOf(savedStateHandle.toRoute<Routes.Sources>().category)

    private var _uiState = MutableStateFlow(SourcesUiState(category = category))
    val uiState = _uiState.asStateFlow()

    init {
        getSourcesByCategory(category)
    }

    private fun getSourcesByCategory(category: Category) {
        repository.getNewsSources(category = category).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(status = UiStatus.SUCCESS, sources = response.data)
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