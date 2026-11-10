package com.reabuilt.a94761525castlecustom.ui.find

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.LocalNavController
import com.reabuilt.a94761525castlecustom.data.model.ContentType
import com.reabuilt.a94761525castlecustom.ui.components.CategoryChip
import com.reabuilt.a94761525castlecustom.ui.components.ContentCardWide
import com.reabuilt.a94761525castlecustom.ui.components.FilterChipComponent
import com.reabuilt.a94761525castlecustom.viewmodel.localMainViewModel

@Composable
fun FindScreen() {
    val viewModel = localMainViewModel()
    val navController = LocalNavController.current
    val all by viewModel.allContent.collectAsState()

    var selectedType by remember { mutableStateOf<ContentType?>(null) }
    var selectedGenre by remember { mutableStateOf<String?>(null) }

    val typeOptions = remember {
        listOf<ContentType?>(
            null,
            ContentType.MOVIE,
            ContentType.TV_SHOW,
            ContentType.WEB_SERIES,
            ContentType.ANIME,
            ContentType.DOCUMENTARY
        )
    }
    val genreOptions = remember {
        listOf<String?>(null, "Action", "Thriller", "Drama", "Comedy", "Crime", "Fantasy")
    }

    val filtered = remember(all, selectedType, selectedGenre) {
        all.filter { content ->
            val typeMatch = selectedType == null || content.contentType == selectedType
            val genreMatch = selectedGenre == null ||
                content.genres.any { genre -> genre.equals(selectedGenre, ignoreCase = true) }
            typeMatch && genreMatch
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Find",
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

        Surface(
            onClick = { navController.navigate("live_tv") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LiveTv,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Watch Live TV",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "News, sports, music and more — streaming now",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Open Live TV",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 14.dp)
        ) {
            typeOptions.forEach { type ->
                CategoryChip(
                    label = type?.displayName ?: "All",
                    isSelected = type == selectedType,
                    onClick = { selectedType = type }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 12.dp)
        ) {
            genreOptions.forEach { genre ->
                FilterChipComponent(
                    label = genre ?: "All Genres",
                    isSelected = genre == selectedGenre,
                    onClick = { selectedGenre = genre }
                )
            }
        }

        if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No matches",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try a different category or genre.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filtered) { content ->
                    ContentCardWide(
                        content = content,
                        onClick = { navController.navigate("detail/${content.id}") },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
