package com.mnhyim.data.remote

import com.mnhyim.data.dto.responses.SourcesApiResponse
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language

interface NewsApi {
    //    suspend fun searchNews(query: String, lang: Language): List<News>
    suspend fun getNewsSources(
        category: Category? = null,
        lang: Language? = null,
        country: Country? = null
    ): SourcesApiResponse
//    suspend fun getTopHeadlinesByCountry(country: Country)
//    suspend fun getTopHeadlinesByCategory(category: Category)
//    suspend fun getTopHeadlinesBySources(sources: String)
}