package com.mnhyim.data.remote

import com.mnhyim.data.dto.responses.SourcesApiResponse
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.domain.model.enums.Country
import com.mnhyim.domain.model.enums.Language
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class NewsApiImpl(
    private val httpClient: HttpClient
): NewsApi {

    private val BASE_URL = "https://newsapi.org/v2/"

    override suspend fun getNewsSources(
        category: Category,
        lang: Language,
        country: Country
    ): SourcesApiResponse {
        val sources: SourcesApiResponse = httpClient.get(BASE_URL + "top-headlines/sources?apiKey=a8ddfed7b99c4f2483649d694b79b0fb").body()
        return sources
    }
}