package com.mnhyim.nyius.ui.feature.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Source
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mnhyim.nyius.ui.components.SourceCard
import com.mnhyim.nyius.ui.components.TitledTopAppBar
import com.mnhyim.nyius.ui.navigation.Routes
import com.mnhyim.nyius.ui.util.UiStatus
import org.koin.androidx.compose.koinViewModel

@Composable
fun SourcesScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Routes) -> Unit,
    viewModel: SourcesViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TitledTopAppBar(
                title = uiState.category?.title ?: "",
                subtitle = "Top ${uiState.category?.title} news sources",
                icon = Icons.Default.Source
            )
        }
    ) { innerPadding ->
        Sources(
            uiState = uiState,
            onNavigate = onNavigate,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun Sources(
    uiState: SourcesUiState,
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = uiState.sources) { source ->
                        SourceCard(
                            source = source,
                            onClick = {
                                onNavigate(
                                    Routes.News(
                                        uiState.category.title,
                                        source.id,
                                        source.name
                                    )
                                )
                            },
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