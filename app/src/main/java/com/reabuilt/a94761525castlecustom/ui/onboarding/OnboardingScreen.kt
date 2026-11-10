package com.reabuilt.a94761525castlecustom.ui.onboarding

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

object OnboardingState {
    private const val PREFS_NAME = "castle_prefs"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

    fun hasOnboarded(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ONBOARDING_COMPLETED, false)

    fun markOnboarded(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_ONBOARDING_COMPLETED, true)
            .apply()
    }
}

private data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tint: Color
)

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val context = LocalContext.current
    var page by remember { mutableIntStateOf(0) }

    val pages = listOf(
        OnboardingPage(
            "Watch Anything",
            "Movies, web series and anime in one place — stream instantly in HD quality.",
            Icons.Default.Movie,
            MaterialTheme.colorScheme.primary
        ),
        OnboardingPage(
            "Live TV & Sports",
            "Follow your favourite channels and live matches with real-time streaming.",
            Icons.Default.LiveTv,
            MaterialTheme.colorScheme.tertiary
        ),
        OnboardingPage(
            "Download & Go Offline",
            "Save your favourite shows and movies to watch anywhere, even without internet.",
            Icons.Default.Download,
            MaterialTheme.colorScheme.secondary
        )
    )
    val current = pages[page]

    fun finish() {
        OnboardingState.markOnboarded(context)
        onComplete()
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { finish() }) {
                    Text("Skip")
                }
                Button(onClick = { if (page < pages.size - 1) page++ else finish() }) {
                    Text(if (page < pages.size - 1) "Next" else "Get Started")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = current.tint.copy(alpha = 0.12f),
                shape = CircleShape,
                modifier = Modifier.size(140.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(current.icon, null, Modifier.size(64.dp), current.tint)
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = current.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = current.subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(if (index == page) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == page) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant
                            )
                    )
                }
            }
        }
    }
}
