package com.mnhyim.data.dto.responses

import com.mnhyim.data.dto.SourceDetailDto
import kotlinx.serialization.Serializable

@Serializable
data class SourcesApiResponse(
    val status: String,
    val sources: List<SourceDetailDto>
)