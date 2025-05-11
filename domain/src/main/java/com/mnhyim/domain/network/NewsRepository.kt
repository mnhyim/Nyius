package com.mnhyim.domain.network

import com.mnhyim.domain.model.News
import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    //    suspend fun searchNews(query: String, lang: Language): Flow<Resource<List<News>>>
    fun getNewsSources(
        category: Category? = null,
        lang: Language? = null,
        country: Country? = null
    ): Flow<Resource<List<SourceDetail>>>
//    suspend fun getTopHeadlinesByCountry(country: Country)
//    suspend fun getTopHeadlinesByCategory(category: Category)
    fun getTopHeadlinesBySources(sources: String): Flow<Resource<List<News>>>
}