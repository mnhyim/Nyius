package com.mnhyim.nyius.ui.navigation

import com.mnhyim.domain.model.enums.Category
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object Home: Routes()

    @Serializable
    data class Sources(val category: String): Routes()

    @Serializable
    data class News(val sources: String): Routes()
}