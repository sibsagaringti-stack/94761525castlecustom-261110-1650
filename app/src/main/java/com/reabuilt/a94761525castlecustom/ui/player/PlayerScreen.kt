package com.reabuilt.a94761525castlecustom.ui.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PlayerScreen(
    contentId: String,
    episodeId: String? = null,
    isLiveChannel: Boolean = false,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLiveChannel) "Live Channel Player" else "Video Player",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Content ID: $contentId",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        if (episodeId != null) {
            Text(
                text = "Episode: $episodeId",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(
            onClick = { onNavigateBack() },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Back")
        }
    }
}
