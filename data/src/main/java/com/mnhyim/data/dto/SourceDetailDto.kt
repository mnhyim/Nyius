package com.mnhyim.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SourceDetailDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val url: String = "",
    val category: String = "",
    val language: String = "",
    val country: String = ""
)