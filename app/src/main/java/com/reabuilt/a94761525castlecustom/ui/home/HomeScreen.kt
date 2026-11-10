package com.reabuilt.a94761525castlecustom.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.findNavController
import coil.compose.AsyncImage
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_SEARCH
import com.reabuilt.a94761525castlecustom.ui.components.ContentRow
import com.reabuilt.a94761525castlecustom.ui.components.ContentRowShimmer
import com.reabuilt.a94761525castlecustom.viewmodel.rememberMainViewModel

@Composable
fun HomeScreen() {
    val viewModel = rememberMainViewModel()
    val navController = findNavController()

    val trending by viewModel.trendingContent.collectAsState()
    val continueWatching by viewModel.continueWatching.collectAsState()
    val recommended by viewModel.recommendedContent.collectAsState()
    val collections by viewModel.featuredCollections.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()
    val history by viewModel.watchHistory.collectAsState()

    val progressMap = remember(history) { history.associate { it.contentId to it.progress / 100f } }
    val watchlistMap = remember(watchlist) { watchlist.associateWith { true } }
    val hero = trending.firstOrNull()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Castle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Castle Custom",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController.navigate(ROUTE_SEARCH) }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (trending.isEmpty()) {
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                ContentRowShimmer()
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                ContentRowShimmer()
                Spacer(modifier = Modifier.height(24.dp))
            }
            item { ContentRowShimmer() }
        } else {
            if (hero != null) {
                item {
                    HeroBanner(
                        content = hero,
                        onPlay = { navController.navigate("player/${hero.id}") },
                        onDetails = { navController.navigate("detail/${hero.id}") }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            item {
                ContentRow(
                    title = "Continue Watching",
                    contentList = continueWatching,
                    showProgress = true,
                    progressMap = progressMap,
                    watchlistMap = watchlistMap,
                    onWatchlistToggle = { viewModel.toggleWatchlist(it.id) },
                    onContentClick = { navController.navigate("detail/${it.id}") }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                ContentRow(
                    title = "Trending Now",
                    contentList = trending,
                    watchlistMap = watchlistMap,
                    onWatchlistToggle = { viewModel.toggleWatchlist(it.id) },
                    onContentClick = { navController.navigate("detail/${it.id}") }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            collections.forEach { collection ->
                if (collection.content.isNotEmpty()) {
                    item {
                        ContentRow(
                            title = collection.title,
                            contentList = collection.content,
                            watchlistMap = watchlistMap,
                            onWatchlistToggle = { viewModel.toggleWatchlist(it.id) },
                            onContentClick = { navController.navigate("detail/${it.id}") }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            item {
                ContentRow(
                    title = "Recommended For You",
                    contentList = recommended,
                    watchlistMap = watchlistMap,
                    onWatchlistToggle = { viewModel.toggleWatchlist(it.id) },
                    onContentClick = { navController.navigate("detail/${it.id}") }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun HeroBanner(
    content: Content,
    onPlay: () -> Unit,
    onDetails: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        AsyncImage(
            model = content.backdropUrl ?: content.posterUrl,
            contentDescription = content.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC100F12), Color(0xF2100F12))
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD54F),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${content.rating}  ·  ${content.releaseYear}  ·  ${content.quality.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
            Text(
                text = content.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.75f),
                maxLines = 2,
                modifier = Modifier.padding(top = 6.dp)
            )
            Row(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onPlay) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Play")
                }
                OutlinedButton(onClick = onDetails) {
                    Text("Details")
                }
            }
        }
    }
}
