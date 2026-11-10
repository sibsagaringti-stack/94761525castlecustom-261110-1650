package com.reabuilt.a94761525castlecustom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.reabuilt.a94761525castlecustom.ui.screens.CastleApp
import com.reabuilt.a94761525castlecustom.ui.theme.CastleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as CastleApplication
        setContent {
            val preferences by app.contentRepository.userPreferences.collectAsState()

            CastleTheme(preferences = preferences) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CastleApp()
                }
            }
        }
    }
}
