package com.mnhyim.domain.model

import com.mnhyim.domain.enum.Category
import com.mnhyim.domain.enum.Country
import com.mnhyim.domain.enum.Language

data class SourceDetail(
    val id: String,
    val name: String,
    val desc: String,
    val url: String,
    val category: Category,
    val language: Language,
    val country: Country
)