package com.reabuilt.a94761525castlecustom.ui.livetv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.reabuilt.a94761525castlecustom.data.model.LiveTvCategory

@Composable
fun LiveTvScreen(
    onChannelClick: (String) -> Unit
) {
    val viewModel: CastleViewModel = viewModel(
        factory = CastleViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext as com.reabuilt.a94761525castlecustom.CastleApplication
        )
    )

    val liveChannels by viewModel.liveChannels.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Live TV Channels",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(LiveTvCategory.entries) { category ->
            val channels = liveChannels.filter { it.category == category }
            if (channels.isNotEmpty()) {
                CategorySection(
                    category = category,
                    channels = channels,
                    onChannelClick = onChannelClick
                )
            }
        }
    }
}

@Composable
fun CategorySection(
    category: LiveTvCategory,
    channels: List<com.reabuilt.a94761525castlecustom.data.model.LiveChannel>,
    onChannelClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = category.displayName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            channels.forEach { channel ->
                ChannelCard(
                    channel = channel,
                    onClick = { onChannelClick(channel.id) }
                )
            }
        }
    }
}

@Composable
fun ChannelCard(
    channel: com.reabuilt.a94761525castlecustom.data.model.LiveChannel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = channel.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "CH ${channel.channelNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (channel.currentProgram != null) {
                Text(
                    text = "Now: ${channel.currentProgram}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (channel.nextProgram != null) {
                Text(
                    text = "Next: ${channel.nextProgram}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

import androidx.compose.foundation.layout.Row
