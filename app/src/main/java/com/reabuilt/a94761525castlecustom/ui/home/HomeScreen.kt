package com.reabuilt.a94761525castlecustom.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.navigation.compose.LocalNavController
import coil.compose.AsyncImage
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.ui.components.ContentRow
import com.reabuilt.a94761525castlecustom.viewmodel.localMainViewModel

@Composable
fun HomeScreen() {
    val viewModel = localMainViewModel()
    val navController = LocalNavController.current

    val trending by viewModel.trendingContent.collectAsState()
    val continueWatching by viewModel.continueWatching.collectAsState()
    val recommended by viewModel.recommendedContent.collectAsState()
    val collections by viewModel.featuredCollections.collectAsState()
    val watchHistory by viewModel.watchHistory.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()

    val progressMap = remember(watchHistory) {
        watchHistory.associate { item -> item.contentId to item.progress / 100f }
    }
    val watchlistMap = remember(watchlist) { watchlist.associateWith { true } }
    val featured = remember(trending) { trending.filter { it.isFeatured } }

    val onContentClick: (Content) -> Unit = { content ->
        navController.navigate("detail/${content.id}")
    }
    val onWatchlistToggle: (Content) -> Unit = { content ->
        viewModel.toggleWatchlist(content.id)
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Castle,
                    contentDescription = null,
                    modifier = Modifier.size(34.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Castle",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController.navigate("search") }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (featured.isNotEmpty()) {
            item {
                FeaturedCarousel(
                    items = featured,
                    onPlay = { content -> navController.navigate("player/${content.id}") },
                    onInfo = onContentClick
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            ContentRow(
                title = "Trending Now",
                contentList = trending,
                onContentClick = onContentClick,
                watchlistMap = watchlistMap,
                onWatchlistToggle = onWatchlistToggle
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            ContentRow(
                title = "Continue Watching",
                contentList = continueWatching,
                onContentClick = onContentClick,
                showProgress = true,
                progressMap = progressMap,
                watchlistMap = watchlistMap,
                onWatchlistToggle = onWatchlistToggle
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        collections.forEach { collection ->
            item(key = collection.id) {
                ContentRow(
                    title = collection.title,
                    contentList = collection.content,
                    onContentClick = onContentClick,
                    watchlistMap = watchlistMap,
                    onWatchlistToggle = onWatchlistToggle
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        item {
            ContentRow(
                title = "Recommended For You",
                contentList = recommended,
                onContentClick = onContentClick,
                watchlistMap = watchlistMap,
                onWatchlistToggle = onWatchlistToggle
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FeaturedCarousel(
    items: List<Content>,
    onPlay: (Content) -> Unit,
    onInfo: (Content) -> Unit
) {
    val pagerState = rememberPagerState { items.size }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
            val content = items.getOrNull(index) ?: items.firstOrNull() ?: return@HorizontalPager
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = content.backdropUrl ?: content.posterUrl,
                    contentDescription = content.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                )
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = content.quality.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { onPlay(content) }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Play")
                        }
                        OutlinedButton(onClick = { onInfo(content) }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Info", color = Color.White)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                ) {
                    items.forEachIndexed { i, _ ->
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(
                                    if (i == pagerState.currentPage) Color.White
                                    else Color.White.copy(alpha = 0.4f)
                                )
                        )
                        if (i < items.lastIndex) Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
    }
}
