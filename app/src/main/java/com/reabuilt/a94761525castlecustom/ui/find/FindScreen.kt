package com.reabuilt.a94761525castlecustom.ui.find

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reabuilt.a94761525castlecustom.CastleViewModel
import com.reabuilt.a94761525castlecustom.CastleViewModelFactory
import com.reabuilt.a94761525castlecustom.ui.components.ContentCard

@Composable
fun FindScreen(
    onContentClick: (String) -> Unit
) {
    val viewModel: CastleViewModel = viewModel(
        factory = CastleViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext as com.reabuilt.a94761525castlecustom.CastleApplication
        )
    )

    val recommended by viewModel.recommendedContent.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Browse All",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recommended) { content ->
                ContentCard(
                    content = content,
                    onClick = { onContentClick(content.id) },
                    onWatchlistToggle = { viewModel.toggleWatchlist(content.id) }
                )
            }
        }
    }
}
