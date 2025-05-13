package com.mnhyim.nyius.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object Home : Routes()

    @Serializable
    data object Search : Routes()

    @Serializable
    data class Sources(val category: String) : Routes()

    @Serializable
    data class News(val category: String, val sources: String, val sourcesName: String) : Routes()
}