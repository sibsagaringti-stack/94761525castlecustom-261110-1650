package com.reabuilt.a94761525castlecustom.ui.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.LocalNavController
import coil.compose.AsyncImage
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.data.model.ContentType
import com.reabuilt.a94761525castlecustom.data.model.Episode
import com.reabuilt.a94761525castlecustom.data.model.Season
import com.reabuilt.a94761525castlecustom.ui.components.ContentCardSize
import com.reabuilt.a94761525castlecustom.ui.components.ContentRow
import com.reabuilt.a94761525castlecustom.ui.components.FilterChipComponent
import com.reabuilt.a94761525castlecustom.viewmodel.rememberMainViewModel

@Composable
fun DetailScreen(contentId: String) {
    val viewModel = rememberMainViewModel()
    val navController = LocalNavController.current
    val context = LocalContext.current

    val watchlist by viewModel.watchlist.collectAsState()
    val content = viewModel.getContent(contentId)

    if (content == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This title is no longer available",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
        return
    }

    val isSeries = content.contentType == ContentType.WEB_SERIES ||
        content.contentType == ContentType.TV_SHOW ||
        content.contentType == ContentType.ANIME
    val seasons = remember(content) { buildMockSeasons(content, isSeries) }
    var selectedSeasonIndex by remember { mutableStateOf(0) }
    val inWatchlist = content.id in watchlist
    val similar = remember(content) {
        viewModel.allContent().filter { it.id != content.id }.take(6)
    }

    val firstEpisodeId = seasons.firstOrNull()?.episodes?.firstOrNull()?.let {
        "S${it.seasonNumber}E${it.episodeNumber}"
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
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
                                colors = listOf(Color.Transparent, Color(0xE6100F12))
                            )
                        )
                )

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        QualityChip(text = content.quality.displayName)
                        QualityChip(text = content.ageRating)
                        if (content.isLive) QualityChip(text = "LIVE")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${content.rating} (${content.voteCount} votes)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        val durationText = content.durationMinutes?.let { "${it} min" }
                            ?: "${content.seasonCount} seasons · ${content.episodeCount} episodes"
                        Text(
                            text = durationText,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content.genres.joinToString("  ·  "),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.75f),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 3
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        if (isSeries && firstEpisodeId != null) {
                            navController.navigate("player/${content.id}/$firstEpisodeId")
                        } else {
                            navController.navigate("player/${content.id}")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isSeries) "Play S1 E1" else "Play")
                }

                OutlinedButton(
                    onClick = { viewModel.toggleWatchlist(content.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (inWatchlist) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (inWatchlist) "Saved" else "Watchlist")
                }

                OutlinedButton(
                    onClick = {
                        viewModel.startDownload(content, if (isSeries) firstEpisodeId else null)
                        Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Download,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save")
                }
            }
        }

        if (isSeries) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    seasons.forEachIndexed { index, season ->
                        FilterChipComponent(
                            label = season.name,
                            isSelected = index == selectedSeasonIndex,
                            onClick = { selectedSeasonIndex = index }
                        )
                    }
                }
            }
            item {
                val episodes = seasons.getOrNull(selectedSeasonIndex)?.episodes ?: emptyList()
                Column(modifier = Modifier.fillMaxWidth()) {
                    episodes.forEach { episode ->
                        EpisodeRow(
                            episode = episode,
                            onClick = {
                                navController.navigate(
                                    "player/${content.id}/S${episode.seasonNumber}E${episode.episodeNumber}"
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (content.cast.isNotEmpty()) {
                    Text(
                        text = "Cast",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        content.cast.forEach { actor ->
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = actor,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                    if (content.crew.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = content.crew.joinToString("  ·  "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Text(
                    text = "Languages",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    content.languages.forEach { language ->
                        FilterChipComponent(
                            label = language,
                            isSelected = false,
                            onClick = { }
                        )
                    }
                }
            }
        }

        item {
            if (similar.isNotEmpty()) {
                ContentRow(
                    title = "More Like This",
                    contentList = similar,
                    cardSize = ContentCardSize.SMALL,
                    onContentClick = { navController.navigate("detail/${it.id}") }
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun QualityChip(text: String) {
    Surface(
        color = Color.Black.copy(alpha = 0.55f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun EpisodeRow(episode: Episode, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = "S${episode.seasonNumber} E${episode.episodeNumber} — ${episode.title}",
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = episode.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
        Text(
            text = "${episode.durationMinutes}m",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun buildMockSeasons(content: Content, isSeries: Boolean): List<Season> {
    if (!isSeries) return emptyList()
    val seasonCount = content.seasonCount.coerceAtLeast(1)
    return (1..seasonCount).map { seasonNumber ->
        val totalEpisodes = content.episodeCount.coerceAtLeast(seasonCount * 2)
        val episodesPerSeason = totalEpisodes / seasonCount
        Season(
            seasonNumber = seasonNumber,
            name = "Season $seasonNumber",
            episodeCount = episodesPerSeason,
            episodes = (1..episodesPerSeason).map { episodeNumber ->
                Episode(
                    episodeNumber = episodeNumber,
                    seasonNumber = seasonNumber,
                    title = "Episode $episodeNumber",
                    description = "Season $seasonNumber, Episode $episodeNumber of ${content.title}.",
                    durationMinutes = 32 + ((seasonNumber * 7 + episodeNumber * 13) % 28),
                    streamUrls = mapOf(
                        "480p" to "https://example.com/videos/${content.id}/s${seasonNumber}e${episodeNumber}_480p.m3u8",
                        "720p" to "https://example.com/videos/${content.id}/s${seasonNumber}e${episodeNumber}_720p.m3u8",
                        "1080p" to "https://example.com/videos/${content.id}/s${seasonNumber}e${episodeNumber}_1080p.m3u8"
                    ),
                    subtitleUrls = emptyMap(),
                    thumbnailUrl = content.posterUrl,
                    airDate = null
                )
            }
        )
    }
}
