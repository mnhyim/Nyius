package com.mnhyim.nyius.ui.feature.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mnhyim.domain.model.News
import com.mnhyim.nyius.ui.components.ErrorMessage
import com.mnhyim.nyius.ui.components.NewsCard2
import com.mnhyim.nyius.ui.components.TitledTopAppBar
import com.mnhyim.nyius.ui.navigation.Routes
import com.mnhyim.nyius.ui.util.launchCustomTabs
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val news = viewModel.getPagedNews(viewModel.source).collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TitledTopAppBar(
                title = uiState.source,
                subtitle = "${uiState.category.title} news from ${uiState.source}",
                icon = Icons.Default.Newspaper
            )
        }
    ) { innerPadding ->
        News(
            items = news,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun News(
    items: LazyPagingItems<News>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey { it.url }
            ) { id ->
                items[id]?.let { news ->
                    NewsCard2(
                        news = news,
                        onClick = {
                            context.launchCustomTabs(
                                url = news.url,
                                useIncognito = false
                            )
                        }
                    )
                }
            }
            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = items.loadState.refresh as LoadState.Error

                        item {
                            ErrorMessage(
                                title = "Error",
                                subtitle = error.error.localizedMessage!!,
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = items.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                title = "Error",
                                subtitle = error.error.localizedMessage!!,
                            )
                        }
                    }
                }
            }
        }
    }
}

