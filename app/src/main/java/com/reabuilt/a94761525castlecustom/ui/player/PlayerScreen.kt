package com.reabuilt.a94761525castlecustom.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerScreen(
    contentId: String,
    episodeId: String? = null,
    isLiveChannel: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Player", style = MaterialTheme.typography.headlineLarge)
            Text("Content: $contentId")
            if (episodeId != null) {
                Text("Episode: $episodeId")
            }
            if (isLiveChannel) {
                Text("Live Channel")
            }
        }
    }
}
