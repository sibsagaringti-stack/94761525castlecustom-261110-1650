package com.reabuilt.a94761525castlecustom.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reabuilt.a94761525castlecustom.CastleViewModel
import com.reabuilt.a94761525castlecustom.CastleViewModelFactory
import com.reabuilt.a94761525castlecustom.ui.components.ContentCard

@Composable
fun HomeScreen(
    onContentClick: (String) -> Unit,
    onNavigateToLiveTv: () -> Unit
) {
    val viewModel: CastleViewModel = viewModel(
        factory = CastleViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext as com.reabuilt.a94761525castlecustom.CastleApplication
        )
    )

    val trending by viewModel.trendingContent.collectAsState()
    val continueWatching by viewModel.continueWatching.collectAsState()
    val featured by viewModel.featuredCollections.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Discover Amazing Content",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Continue Watching
        if (continueWatching.isNotEmpty()) {
            item {
                SectionHeader(title = "Continue Watching")
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(continueWatching) { content ->
                        ContentCard(
                            content = content,
                            onClick = { onContentClick(content.id) },
                            onWatchlistToggle = { viewModel.toggleWatchlist(content.id) }
                        )
                    }
                }
            }
        }

        // Trending
        if (trending.isNotEmpty()) {
            item {
                SectionHeader(title = "Trending Now")
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(trending.take(10)) { content ->
                        ContentCard(
                            content = content,
                            onClick = { onContentClick(content.id) },
                            onWatchlistToggle = { viewModel.toggleWatchlist(content.id) }
                        )
                    }
                }
            }
        }

        // Featured Collections
        items(featured) { collection ->
            SectionHeader(title = collection.title)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(collection.content) { content ->
                    ContentCard(
                        content = content,
                        onClick = { onContentClick(content.id) },
                        onWatchlistToggle = { viewModel.toggleWatchlist(content.id) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun SectionHeader(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
}
