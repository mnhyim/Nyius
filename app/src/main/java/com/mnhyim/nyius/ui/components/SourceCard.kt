package com.mnhyim.nyius.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mnhyim.domain.model.SourceDetail

@Composable
fun SourceCard(
    source: SourceDetail,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                Text(
                    text = source.name,
                    style = MaterialTheme.typography.titleSmall,
                )
//                Text(
//                    text = "[${source.country}] - [${source.language}]",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                )
            }
            Text(
                text = source.desc,
                style = MaterialTheme.typography.labelSmall,
//                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}