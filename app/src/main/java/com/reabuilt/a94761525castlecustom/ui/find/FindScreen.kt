package com.reabuilt.a94761525castlecustom.ui.find

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.LocalNavController
import com.reabuilt.a94761525castlecustom.data.model.ContentType
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_SEARCH
import com.reabuilt.a94761525castlecustom.ui.components.CategoryChip
import com.reabuilt.a94761525castlecustom.ui.components.ContentCard
import com.reabuilt.a94761525castlecustom.ui.components.ContentCardSize
import com.reabuilt.a94761525castlecustom.viewmodel.rememberMainViewModel

@Composable
fun FindScreen() {
    val viewModel = rememberMainViewModel()
    val navController = LocalNavController.current

    val trending by viewModel.trendingContent.collectAsState()
    val recommended by viewModel.recommendedContent.collectAsState()
    val collections by viewModel.featuredCollections.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()

    val all = remember(trending, recommended, collections) {
        (trending + recommended + collections.flatMap { it.content }).distinctBy { it.id }
    }

    var selectedType by remember { mutableStateOf<ContentType?>(null) }
    var selectedGenre by remember { mutableStateOf<String?>(null) }

    val genres = remember(all) { all.flatMap { it.genres }.distinct().sorted() }

    val filtered = remember(all, selectedType, selectedGenre) {
        all.filter { content ->
            (selectedType == null || content.contentType == selectedType) &&
                (selectedGenre == null || content.genres.contains(selectedGenre))
        }
    }

    val typeLabels = listOf(
        ContentType.MOVIE to "Movies",
        ContentType.TV_SHOW to "TV Shows",
        ContentType.WEB_SERIES to "Web Series",
        ContentType.ANIME to "Anime",
        ContentType.DOCUMENTARY to "Documentaries",
        ContentType.SHORT to "Shorts"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Find",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${filtered.size} titles",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { navController.navigate(ROUTE_SEARCH) },
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Search movies, series, genres…",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            CategoryChip(
                label = "All",
                isSelected = selectedType == null,
                onClick = { selectedType = null }
            )
            typeLabels.forEach { (type, label) ->
                CategoryChip(
                    label = label,
                    isSelected = selectedType == type,
                    onClick = { selectedType = if (selectedType == type) null else type }
                )
            }
        }

        if (genres.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                genres.forEach { genre ->
                    CategoryChip(
                        label = genre,
                        isSelected = selectedGenre == genre,
                        onClick = { selectedGenre = if (selectedGenre == genre) null else genre }
                    )
                }
            }
        }

        if (filtered.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Nothing matches those filters",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try selecting a different type or genre.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(filtered, key = { it.id }) { content ->
                        ContentCard(
                            content = content,
                            size = ContentCardSize.MEDIUM,
                            onClick = { navController.navigate("detail/${content.id}") },
                            isWatchlisted = content.id in watchlist,
                            onToggleWatchlist = { viewModel.toggleWatchlist(content.id) }
                        )
                    }
                },
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
        }
    }
}
