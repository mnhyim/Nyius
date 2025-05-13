package com.mnhyim.data.remote

import com.mnhyim.data.dto.responses.NewsApiResponse
import com.mnhyim.data.dto.responses.SourcesApiResponse
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class NewsApiImpl(
    private val httpClient: HttpClient
) : NewsApi {

    private val BASE_URL = "https://newsapi.org/v2/"
    private val DUMMY_API_KEY = ""

    override suspend fun getNewsSources(
        category: Category,
        lang: Language,
        country: Country
    ): SourcesApiResponse {
        val sources: SourcesApiResponse =
            httpClient.get(BASE_URL + "top-headlines/sources?category=${category.name}&language=${lang.name}&country=${country.name}&apiKey=${DUMMY_API_KEY}")
                .body()
        return sources
    }

    override suspend fun getTopHeadlinesBySources(sources: String, page: Int): NewsApiResponse {
        val news: NewsApiResponse =
            httpClient.get(BASE_URL + "everything?sources=${sources}&page=$page&pageSize=5&apiKey=${DUMMY_API_KEY}")
                .body()
        return news
    }

    override suspend fun searchNews(
        query: String,
        page: Int
    ): NewsApiResponse {
        val news: NewsApiResponse =
            httpClient.get(BASE_URL + "everything?q=${query}&page=$page&pageSize=5&apiKey=${DUMMY_API_KEY}")
                .body()
        return news
    }
}