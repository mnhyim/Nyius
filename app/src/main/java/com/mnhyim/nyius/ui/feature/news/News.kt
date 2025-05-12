package com.mnhyim.nyius.ui.feature.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mnhyim.nyius.ui.components.NewsCard2
import com.mnhyim.nyius.ui.components.TitledTopAppBar
import com.mnhyim.nyius.ui.navigation.Routes
import com.mnhyim.nyius.ui.util.UiStatus
import com.mnhyim.nyius.ui.util.launchCustomTabs
import org.koin.androidx.compose.koinViewModel


@Composable
fun NewsScreen(
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            uiState = uiState,
            onNavigate = onNavigate,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun News(
    uiState: NewsUiState,
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        when (uiState.status) {
            UiStatus.LOADING -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Loading",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            UiStatus.SUCCESS -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = uiState.news) { news ->
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
            }

            UiStatus.ERROR -> {
                Text("Error")
            }
        }
    }
}

