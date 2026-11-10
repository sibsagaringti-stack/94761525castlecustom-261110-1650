package com.reabuilt.a94761525castlecustom.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ContentCardShimmer(
    size: ContentCardSize = ContentCardSize.MEDIUM,
    modifier: Modifier = Modifier
) {
    val cardWidth = when (size) {
        ContentCardSize.SMALL -> 112.dp
        ContentCardSize.MEDIUM -> 140.dp
        ContentCardSize.LARGE -> 168.dp
        ContentCardSize.XLARGE -> 200.dp
    }
    val cardHeight = when (size) {
        ContentCardSize.SMALL -> 180.dp
        ContentCardSize.MEDIUM -> 220.dp
        ContentCardSize.LARGE -> 260.dp
        ContentCardSize.XLARGE -> 300.dp
    }

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, delayMillis = 0),
            repeatMode = RepeatMode.Reverse
        )
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
            MaterialTheme.colorScheme.surface.copy(alpha = alpha),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha)
        )
    )

    Card(
        modifier = modifier.width(cardWidth).height(cardHeight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(shimmerBrush)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(shimmerBrush, RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(12.dp)
                        .background(shimmerBrush, RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(10.dp)
                        .background(shimmerBrush, RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

@Composable
fun ContentRowShimmer(
    cardSize: ContentCardSize = ContentCardSize.MEDIUM,
    count: Int = 5
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, delayMillis = 0),
            repeatMode = RepeatMode.Reverse
        )
    )
    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
            MaterialTheme.colorScheme.surface.copy(alpha = alpha),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha)
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(24.dp)
                    .background(shimmerBrush, RoundedCornerShape(4.dp))
            )
        }

        LazyRow(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count) {
                ContentCardShimmer(size = cardSize)
            }
        }
    }
}
