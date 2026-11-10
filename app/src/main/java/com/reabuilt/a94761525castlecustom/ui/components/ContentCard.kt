package com.reabuilt.a94761525castlecustom.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.Crossfade
import coil3.compose.dataUrl
import coil3.compose.placeholder
import coil3.compose.requestSuccess
import coil3.request.ImageRequest
import coil3.transform.CircleCropTransformation
import com.reabuilt.a94761525castlecustom.R
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.data.model.ContentType
import com.reabuilt.a94761525castlecustom.data.model.VideoQuality
import com.reabuilt.a94761525castlecustom.ui.theme.CastleTheme
import kotlinx.coroutines.launch

@Composable
fun ContentCard(
    content: Content,
    size: ContentCardSize = ContentCardSize.MEDIUM,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    showProgress: Boolean = false,
    progress: Float = 0f,
    isInWatchlist: Boolean = false,
    onWatchlistClick: (() -> Unit)? = null
) {
    val cardWidth = when (size) {
        ContentCardSize.SMALL -> 112.dp
        ContentCardSize.MEDIUM -> 140.dp
        ContentCardSize.LARGE -> 168.dp
        ContentCardSize.XLARGE -> 200.dp
    }
    val cardHeight = cardWidth / 0.7f

    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        onClick = onClick,
        onLongClick = onLongClick,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = CastleTheme.colorScheme.surface,
            contentColor = CastleTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Poster Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            ) {
                AsyncImage(
                    model = content.posterUrl,
                    contentDescription = content.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = placeholder {
                        Box(
                            modifier = Modifier.fillMaxSize().background(CastleTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getContentTypeIcon(content.contentType),
                                contentDescription = null,
                                tint = CastleTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    crossfade = Crossfade(true),
                    error = {
                        Box(
                            modifier = Modifier.fillMaxSize().background(CastleTheme.colorScheme.errorContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.BrokenImage,
                                contentDescription = null,
                                tint = CastleTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )

                // Quality Badge
                if (content.quality != VideoQuality.SD) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        QualityBadge(quality = content.quality)
                    }
                }

                // Content Type Badge (for Live TV)
                if (content.isLive || content.contentType == ContentType.LIVE_TV) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                    ) {
                        LiveBadge()
                    }
                }

                // Watch Progress Overlay
                if (showProgress && progress > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            androidx.compose.material3.CircularProgressIndicator(
                                progress = progress / 100f,
                                modifier = Modifier.size(48.dp),
                                strokeWidth = 4.dp,
                                color = CastleTheme.colorScheme.primary,
                                trackColor = CastleTheme.colorScheme.primaryContainer
                            )
                            Text(
                                text = "${progress.toInt()}%",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Watchlist Button Overlay
                if (onWatchlistClick != null) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        IconButton(
                            onClick = onWatchlistClick,
                            modifier = Modifier.background(CastleTheme.colorScheme.surface.copy(alpha = 0.9f), RoundedCornerShape(20.dp))
                        ) {
                            Icon(
                                imageVector = if (isInWatchlist)
                                    androidx.compose.material.icons.Icons.Default.Bookmark
                                else
                                    androidx.compose.material.icons.Icons.Default.BookmarkAddOutline,
                                contentDescription = if (isInWatchlist) "Remove from watchlist" else "Add to watchlist",
                                tint = if (isInWatchlist) CastleTheme.colorScheme.primary else Color.White,
                                modifier = Modifier.size(24.dp).padding(8.dp)
                            )
                        }
                    }
                }
            }

            // Content Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = content.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CastleTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Rating
                    if (content.rating > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Star,
                                contentDescription = null,
                                tint = CastleTheme.colorScheme.secondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = String.format("%.1f", content.rating),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = CastleTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Year
                    Text(
                        text = content.releaseYear.toString(),
                        fontSize = 12.sp,
                        color = CastleTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )

                    // Content Type
                    Text(
                        text = content.contentType.displayName,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = CastleTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                            .background(CastleTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                    )
                }

                // Genres (first 2)
                if (content.genres.isNotEmpty()) {
                    Text(
                        text = content.genres.take(2).joinToString(" • "),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 11.sp,
                        color = CastleTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun QualityBadge(quality: VideoQuality) {
    val (text, color) = when (quality) {
        VideoQuality.SD -> "SD" to CastleTheme.colorScheme.onSurfaceVariant
        VideoQuality.HD -> "HD" to CastleTheme.colorScheme.primary
        VideoQuality.FULL_HD -> "Full HD" to CastleTheme.colorScheme.tertiary
        VideoQuality.ULTRA_HD -> "4K" to CastleTheme.colorScheme.secondary
    }

    Text(
        text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .background(color, RoundedCornerShape(4.dp))
    )
}

@Composable
fun LiveBadge() {
    Box(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .background(CastleTheme.colorScheme.error, RoundedCornerShape(4.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color.White, RoundedCornerShape(3.dp))
            )
            Text(
                text = "LIVE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

enum class ContentCardSize {
    SMALL, MEDIUM, LARGE, XLARGE
}

fun getContentTypeIcon(type: ContentType) = when (type) {
    ContentType.MOVIE -> androidx.compose.material.icons.Icons.Default.Movie
    ContentType.TV_SHOW -> androidx.compose.material.icons.Icons.Default.Tv
    ContentType.WEB_SERIES -> androidx.compose.material.icons.Icons.Default.LiveTv
    ContentType.ANIME -> androidx.compose.material.icons.Icons.Default.Animation
    ContentType.LIVE_TV -> androidx.compose.material.icons.Icons.Default.LiveTv
    ContentType.SHORT -> androidx.compose.material.icons.Icons.Default.Shorts
    ContentType.DOCUMENTARY -> androidx.compose.material.icons.Icons.Default.VideoLibrary
}
