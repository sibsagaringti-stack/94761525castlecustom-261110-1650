package com.reabuilt.a94761525castlecustom.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(contentId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Content Detail", style = MaterialTheme.typography.headlineLarge)
        Text("Content ID: $contentId")
        Button(onClick = {}) {
            Text("Play")
        }
    }
}
