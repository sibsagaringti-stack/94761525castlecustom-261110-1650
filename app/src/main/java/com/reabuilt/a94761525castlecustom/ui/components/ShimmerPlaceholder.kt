package com.reabuilt.a94761525castlecustom.ui.components

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reabuilt.a94761525castlecustom.ui.theme.CastleTheme

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
    val cardHeight = cardWidth / 0.7f

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animationSpec = tween(1000, delayMillis = 0, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        )
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            CastleTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
            CastleTheme.colorScheme.surface.copy(alpha = alpha),
            CastleTheme.colorScheme.surfaceVariant.copy(alpha = alpha)
        ),
        start = androidx.compose.ui.geometry.Offset(0f, 0f),
        end = androidx.compose.ui.geometry.Offset(cardWidth.value, 0f)
    )

    Card(
        modifier = modifier.width(cardWidth).height(cardHeight),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = CastleTheme.colorScheme.surface)
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header shimmer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(24.dp)
                    .background(shimmerBrushForWidth(120.dp), RoundedCornerShape(4.dp))
            )
        }

        // Cards shimmer
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count) {
                ContentCardShimmer(size = cardSize)
            }
        }
    }
}

@Composable
fun shimmerBrushForWidth(width: androidx.compose.ui.unit.Dp): Brush {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animationSpec = tween(1000, delayMillis = 0, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        )
    )
    return Brush.linearGradient(
        colors = listOf(
            CastleTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
            CastleTheme.colorScheme.surface.copy(alpha = alpha),
            CastleTheme.colorScheme.surfaceVariant.copy(alpha = alpha)
        ),
        start = androidx.compose.ui.geometry.Offset(0f, 0f),
        end = androidx.compose.ui.geometry.Offset(width.value, 0f)
    )
}
