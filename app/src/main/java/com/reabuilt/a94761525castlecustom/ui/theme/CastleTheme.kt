package com.reabuilt.a94761525castlecustom.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.reabuilt.a94761525castlecustom.data.model.ThemeMode
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences

@Composable
fun CastleTheme(
    darkTheme: Boolean? = null,
    preferences: UserPreferences? = null,
    content: @Composable () -> Unit
) {
    val resolvedDarkTheme: Boolean = when {
        darkTheme != null -> darkTheme
        preferences != null -> when (preferences.themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
        }
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (resolvedDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
