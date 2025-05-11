package com.mnhyim.domain.network

import com.mnhyim.domain.model.Resource
import com.mnhyim.domain.model.SourceDetail
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
//    suspend fun searchNews(query: String, lang: Language): Flow<Resource<List<News>>>
    suspend fun getNewsSources(category: Category, lang: Language, country: Country): Flow<Resource<List<SourceDetail>>>
//    suspend fun getTopHeadlinesByCountry(country: Country)
//    suspend fun getTopHeadlinesByCategory(category: Category)
//    suspend fun getTopHeadlinesBySources(sources: String)
}