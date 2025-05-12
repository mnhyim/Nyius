package com.mnhyim.nyius.ui.feature.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mnhyim.domain.model.News
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
    val newsList by viewModel.news.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        News(
            uiState = uiState,
            newsList = newsList,
            onNavigate = onNavigate,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun News(
    uiState: UiStatus,
    newsList: List<News>,
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = "News",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        when (uiState) {
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
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(items = newsList) { news ->
                        Card(
                            onClick = {
                                context.launchCustomTabs(
                                    url = news.url,
                                    useIncognito = false
                                )
                            }
                        ) {
                            Row(
                                modifier = Modifier.height(IntrinsicSize.Min)
                            ) {
                                /* TODO: Temporarily uses Icon, will change into image with Coil later */
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(0.2f)
                                        .background(
                                            MaterialTheme.colorScheme.secondary,
                                            RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp)
                                        )
                                )
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = "2 days ago",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    )
                                    Text(
                                        text = news.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                    Text(
                                        text = news.description,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }

            UiStatus.ERROR -> {
                Text("Error")
            }
        }
    }
}

