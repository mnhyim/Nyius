package com.mnhyim.nyius.ui.feature.sources

import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.nyius.ui.util.UiStatus

data class SourcesUiState(
    val status: UiStatus = UiStatus.LOADING,
    val category: Category,
    val error: Throwable? = null,
    val sources: List<SourceDetail> = emptyList()
)