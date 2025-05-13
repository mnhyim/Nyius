package com.mnhyim.data.remote

import com.mnhyim.data.dto.responses.NewsApiResponse
import com.mnhyim.data.dto.responses.SourcesApiResponse
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language

interface NewsApi {
    suspend fun getNewsSources(
        category: Category,
        lang: Language = Language.EN,
        country: Country = Country.US
    ): SourcesApiResponse

    suspend fun getTopHeadlinesBySources(sources: String, page: Int = 1): NewsApiResponse

    suspend fun searchNews(q: String, page: Int = 1): NewsApiResponse
}