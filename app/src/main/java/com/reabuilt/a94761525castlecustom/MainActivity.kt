package com.reabuilt.a94761525castlecustom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.reabuilt.a94761525castlecustom.data.model.ThemeMode
import com.reabuilt.a94761525castlecustom.navigation.CastleNavigation
import com.reabuilt.a94761525castlecustom.viewmodel.rememberMainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = rememberMainViewModel()
            val preferences by viewModel.preferences.collectAsState()

            val colorScheme = when (preferences.themeMode) {
                ThemeMode.DARK -> CastleDarkColors
                ThemeMode.LIGHT -> CastleLightColors
                ThemeMode.SYSTEM ->
                    if (isSystemInDarkTheme()) CastleDarkColors else CastleLightColors
            }

            MaterialTheme(colorScheme = colorScheme) {
                CastleNavigation()
            }
        }
    }
}

val CastleDarkColors = darkColorScheme(
    primary = Color(0xFF7C4DFF),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4F2DB5),
    onPrimaryContainer = Color(0xFFE6DFFF),
    secondary = Color(0xFFCC6770),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF563A3D),
    onSecondaryContainer = Color(0xFFF2CCD0),
    tertiary = Color(0xFFA8657A),
    onTertiary = Color.White,
    background = Color(0xFF100F12),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF100F12),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF2B2930),
    onSurfaceVariant = Color(0xFFC9C5D0),
    error = Color(0xFFEB9B99),
    onError = Color(0xFF6C0500)
)

val CastleLightColors = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF7A5E74),
    onTertiary = Color.White,
    background = Color(0xFFF7F2FA),
    onBackground = Color(0xFF1D1B20),
    surface = Color(0xFFF7F2FA),
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFBA1A1A),
    onError = Color.White
)
