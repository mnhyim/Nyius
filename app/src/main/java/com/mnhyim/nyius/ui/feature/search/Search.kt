package com.mnhyim.nyius.ui.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mnhyim.domain.model.News
import com.mnhyim.nyius.ui.components.ErrorMessage
import com.mnhyim.nyius.ui.components.NewsCard2
import com.mnhyim.nyius.ui.components.TitledTopAppBar
import com.mnhyim.nyius.ui.util.launchCustomTabs
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
) {
    var searchQuery by remember { mutableStateOf("") }
    val news = remember(searchQuery) {
        if (searchQuery.isBlank()) null else viewModel.searchPagedNews(searchQuery)
    }?.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TitledTopAppBar(
                title = "Search",
                subtitle = "Search news by keyword",
                icon = Icons.Default.Newspaper
            )
        }
    ) { innerPadding ->
        Search(
            onSearchQueryChange = { searchQuery = it },
            news = news,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun Search(
    onSearchQueryChange: (String) -> Unit,
    news: LazyPagingItems<News>?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var input by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            placeholder = {
                Text(
                    text = "Search..",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(Icons.Default.Search, "")
            },
            colors = OutlinedTextFieldDefaults.colors(focusedLeadingIconColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchQueryChange(input)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        if (news != null) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    count = news.itemCount,
                    key = news.itemKey { it.url }
                ) { id ->
                    news[id]?.let { news ->
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
                news.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = news.loadState.refresh as LoadState.Error

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
                            val error = news.loadState.refresh as LoadState.Error
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
}