package com.reabuilt.a94761525castlecustom.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reabuilt.a94761525castlecustom.data.model.Content

@Composable
fun ContentRow(
    title: String,
    contentList: List<Content>,
    cardSize: ContentCardSize = ContentCardSize.MEDIUM,
    onContentClick: (Content) -> Unit,
    onSeeAllClick: (() -> Unit)? = null,
    showProgress: Boolean = false,
    progressMap: Map<String, Float> = emptyMap(),
    watchlistMap: Map<String, Boolean> = emptyMap(),
    onWatchlistToggle: ((Content) -> Unit)? = null
) {
    if (contentList.isEmpty()) return

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            onSeeAllClick?.let {
                TextButton(onClick = it) {
                    Text(
                        text = "See All",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            items(contentList) { content ->
                ContentCard(
                    content = content,
                    size = cardSize,
                    onClick = { onContentClick(content) },
                    showWatchProgress = showProgress && progressMap.containsKey(content.id),
                    watchProgress = (progressMap[content.id]?.times(100)?.toInt()) ?: 0,
                    isWatchlisted = watchlistMap[content.id] ?: false,
                    onToggleWatchlist = { onWatchlistToggle?.invoke(content) }
                )
            }
        }
    }
}
