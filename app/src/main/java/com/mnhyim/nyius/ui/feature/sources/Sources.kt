package com.mnhyim.nyius.ui.feature.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import com.mnhyim.domain.model.SourceDetail
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
    val items by viewModel.sources.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        when (uiState) {
            UiStatus.LOADING -> {
                Column {
                    CircularProgressIndicator()
                    Text("Loading")
                }
            }

            UiStatus.SUCCESS -> {
                Sources(
                    title = viewModel.category.title,
                    items = items,
                    onNavigate = onNavigate,
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                )
            }

            UiStatus.ERROR -> {
                Text("Error")
            }
        }

    }
}

@Composable
private fun Sources(
    title: String,
    items: List<SourceDetail>,
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Top $title News Sources",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = items) {
                Card(
                    onClick = {  }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "[${it.country}] - [${it.language}]",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Text(
                            text = it.desc,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}