package com.reabuilt.a94761525castlecustom.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reabuilt.a94761525castlecustom.viewmodel.rememberMainViewModel
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

private val PAGES = listOf(
    OnboardingPage(
        icon = Icons.Filled.PlayCircle,
        title = "Watch Anything, Anywhere",
        subtitle = "Stream thousands of movies, web series and anime in crisp HD — all from a single, beautifully simple library."
    ),
    OnboardingPage(
        icon = Icons.Filled.Theaters,
        title = "No Ads. No Interruptions.",
        subtitle = "Every banner, pre-roll and pop-up advertisement has been removed. What you see is the show — nothing else."
    ),
    OnboardingPage(
        icon = Icons.Filled.Download,
        title = "Free Forever, Download Anytime",
        subtitle = "There is no subscription and no paywall. Download your favourite titles and keep watching offline, always."
    )
)

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val viewModel = rememberMainViewModel()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { PAGES.size }
    val currentPage = pagerState.currentPage
    val isLastPage = currentPage == PAGES.size - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "WELCOME TO CASTLE CUSTOM",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(24.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val pageContent = PAGES[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = pageContent.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(96.dp)
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = pageContent.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = pageContent.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage((currentPage + 1) % PAGES.size)
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PAGES.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentPage) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.markOnboardingCompleted()
                onComplete()
            },
            modifier = Modifier
                .fillMaxSize()
                .height(52.dp)
        ) {
            Text(
                text = if (isLastPage) "Let's Start Watching" else "Get Started",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (isLastPage) {
            TextButton(onClick = {
                viewModel.markOnboardingCompleted()
                onComplete()
            }) {
                Text("Skip", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
