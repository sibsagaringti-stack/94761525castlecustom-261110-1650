package com.reabuilt.a94761525castlecustom.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.ui.theme.CastleTheme

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
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CastleTheme.colorScheme.onSurface
            )
            onSeeAllClick?.let {
                androidx.compose.material3.TextButton(onClick = it) {
                    Text(
                        text = "See All",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = CastleTheme.colorScheme.primary
                    )
                }
            }
        }

        // Content List
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
            state = rememberScrollState()
        ) {
            items(contentList) { content ->
                ContentCard(
                    content = content,
                    size = cardSize,
                    onClick = { onContentClick(content) },
                    showProgress = showProgress && progressMap.containsKey(content.id),
                    progress = progressMap[content.id] ?: 0f,
                    isInWatchlist = watchlistMap[content.id] ?: false,
                    onWatchlistClick = onWatchlistToggle?.let { { it(content) } }
                )
            }
        }
    }
}
