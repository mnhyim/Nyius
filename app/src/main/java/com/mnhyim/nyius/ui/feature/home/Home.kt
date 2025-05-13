package com.mnhyim.nyius.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mnhyim.domain.model.enums.Category
import com.mnhyim.nyius.ui.components.CategoryCard
import com.mnhyim.nyius.ui.components.TitledTopAppBar
import com.mnhyim.nyius.ui.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Routes) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    Scaffold(
        topBar = {
            TitledTopAppBar(
                title = "Categories",
                subtitle = "Browse News by Categories",
                icon = Icons.Default.Category,
                actions = {
                    IconButton(
                        onClick = { onNavigate(Routes.Search) }
                    ) {
                        Icon(Icons.Default.Search, "")
                    }
                }
            )
        }
    ) { innerPadding ->
        Home(
            onNavigate = onNavigate,
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun Home(
    onNavigate: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = Category.entries.toTypedArray()) {
                CategoryCard(
                    category = it,
                    onClick = onNavigate,
                )
            }
        }
    }
}