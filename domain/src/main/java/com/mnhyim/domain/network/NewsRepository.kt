package com.mnhyim.domain.network

import com.mnhyim.domain.model.News
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsSources(
        category: Category,
        lang: Language = Language.EN,
        country: Country = Country.US
    ): Flow<Resource<List<SourceDetail>>>

    fun getTopHeadlinesBySources(sources: String): Flow<Resource<List<News>>>
}