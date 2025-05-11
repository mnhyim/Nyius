package com.mnhyim.nyius.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val testt by viewModel.testt.collectAsStateWithLifecycle()
    Column(modifier = modifier) {
        Text("Test")
        LazyColumn {
            items(items=testt) {
                Text("$it")
            }
        }
        Button(onClick = { viewModel.yrss() }) {
            Text("Test")
        }
    }
}