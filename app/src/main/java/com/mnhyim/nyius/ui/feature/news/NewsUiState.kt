package com.mnhyim.nyius.ui.feature.news

import com.mnhyim.domain.model.News
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.nyius.ui.util.UiStatus

data class NewsUiState(
    val status: UiStatus = UiStatus.LOADING,
    val source: String = "",
    val category: Category,
    val news: List<News> = emptyList(),
)
