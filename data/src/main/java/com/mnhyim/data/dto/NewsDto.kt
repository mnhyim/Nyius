package com.mnhyim.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val source: SourceDto,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val timestamp: String? = "",
    val content: String? = "",
)
