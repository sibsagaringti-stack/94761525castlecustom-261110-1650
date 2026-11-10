package com.reabuilt.a94761525castlecustom.ui.player

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.findNavController
import com.reabuilt.a94761525castlecustom.data.model.VideoQuality
import com.reabuilt.a94761525castlecustom.player.MediaButtonReceiver
import com.reabuilt.a94761525castlecustom.viewmodel.rememberMainViewModel

@Composable
fun PlayerScreen(
    contentId: String,
    episodeId: String? = null,
    isLiveChannel: Boolean = false
) {
    val viewModel = rememberMainViewModel()
    val context = LocalContext.current
    val navController = findNavController()

    val preferences by viewModel.preferences.collectAsState()

    val content = viewModel.getContent(contentId)
    val channel = if (isLiveChannel) viewModel.getLiveChannel(contentId) else null

    val title = remember(content, channel, episodeId) {
        when {
            channel != null -> channel.name
            episodeId != null && content != null -> "${content.title} · $episodeId"
            else -> content?.title ?: "Now Playing"
        }
    }

    val streamUrl = remember(content, channel, episodeId, preferences) {
        when {
            channel != null -> channel.streamUrl
            episodeId != null && content != null ->
                "https://example.com/videos/${content.id}/${episodeId.lowercase()}_720p.m3u8"
            else -> {
                val urls = content?.streamUrls ?: emptyMap()
                val preferred = when (preferences.videoQuality) {
                    VideoQuality.ULTRA_HD -> "2160p"
                    VideoQuality.FULL_HD -> "1080p"
                    VideoQuality.HD -> "720p"
                    VideoQuality.SD -> "480p"
                }
                urls[preferred] ?: urls.values.firstOrNull()
            }
        }
    }

    var playerError by remember { mutableStateOf(false) }
    var retryTrigger by remember { mutableStateOf(0) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
            .build()
    }

    val errorListener = remember {
        object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Handler(Looper.getMainLooper()).post { playerError = true }
            }
        }
    }

    DisposableEffect(Unit) {
        MediaButtonReceiver.activePlayer = exoPlayer
        exoPlayer.addListener(errorListener)
        onDispose {
            exoPlayer.removeListener(errorListener)
            if (MediaButtonReceiver.activePlayer === exoPlayer) {
                MediaButtonReceiver.activePlayer = null
            }
            val duration = exoPlayer.duration
            val position = exoPlayer.currentPosition
            if (!isLiveChannel && duration > 0L && position > 0L) {
                viewModel.recordWatchProgress(contentId, (position * 100L / duration).toInt())
            }
            exoPlayer.release()
        }
    }

    LaunchedEffect(streamUrl, exoPlayer, retryTrigger) {
        if (streamUrl == null) {
            playerError = true
            return@LaunchedEffect
        }
        playerError = false
        val mediaItem = MediaItem.Builder().setUri(streamUrl).build()
        val mediaSourceFactory = DefaultMediaSourceFactory(context)
        exoPlayer.setMediaSource(mediaSourceFactory.createMediaSource(mediaItem))
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = { viewContext ->
                PlayerView(viewContext).apply {
                    setPlayer(exoPlayer)
                    useController = true
                    setShutterBackgroundColor(android.graphics.Color.BLACK)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 8.dp, end = 12.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xAA000000), Color.Transparent)
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isLiveChannel) {
                Surface(color = Color(0xFFDC2626), shape = RoundedCornerShape(4.dp)) {
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        if (playerError) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xF01C1B1F),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 28.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = null,
                        tint = Color(0xFFF87171),
                        modifier = Modifier.size(52.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Stream unavailable",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This demo stream could not be played. Retry, or go back and try another title.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Button(onClick = {
                        retryTrigger++
                        playerError = false
                    }) {
                        Text("Retry")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = { navController.popBackStack() }) {
                        Text("Back")
                    }
                }
            }
        }
    }
}
