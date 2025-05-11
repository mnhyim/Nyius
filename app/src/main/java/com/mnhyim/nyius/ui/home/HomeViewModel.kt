package com.mnhyim.nyius.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import com.mnhyim.domain.network.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    var test = MutableStateFlow(emptyList<SourceDetail>())
    val testt = test.asStateFlow()
    fun yrss() {
        Log.d("A", "AAAA")
        viewModelScope.launch {
            repository.getNewsSources(
                category = Category.ENTERTAINMENT,
                lang = Language.EN,
                country = Country.US
            ).onEach { response ->
                when (response) {
                    is Resource.Loading -> Log.d("A", "Loading")
                    is Resource.Error -> Log.d("A", "Error")
                    is Resource.Success -> test.value = response.data
                }
            }.launchIn(viewModelScope)
        }
    }
}