package com.mnhyim.domain.model

import java.time.LocalDate

data class News(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val timestamp: LocalDate,
    val content: String,
)
