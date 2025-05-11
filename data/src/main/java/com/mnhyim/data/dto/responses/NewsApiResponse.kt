package com.mnhyim.data.dto.responses

import com.mnhyim.data.dto.NewsDto
import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsDto>
)