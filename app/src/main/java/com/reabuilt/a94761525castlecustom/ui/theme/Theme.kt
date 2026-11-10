package com.reabuilt.a94761525castlecustom.ui.theme

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.reabuilt.a94761525castlecustom.R
import com.reabuilt.a94761525castlecustom.data.model.ThemeMode
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B21A8),
    primaryVariant = Color(0xFF581C87),
    secondary = Color(0xFFB45309),
    secondaryVariant = Color(0xFF92400E),
    tertiary = Color(0xFF0F766E),
    tertiaryVariant = Color(0xFF115E59),
    error = Color(0xFFDC2626),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFF5F0FF),
    background = Color(0xFFFAFAFA),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onError = Color.White,
    onSurface = Color(0xFF1C1B1F),
    onSurfaceVariant = Color(0xFF49454F),
    onBackground = Color(0xFF1C1B1F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFC084FC),
    primaryVariant = Color(0xFFA855F7),
    secondary = Color(0xFFFCD34D),
    secondaryVariant = Color(0xFFFBBF24),
    tertiary = Color(0xFF5EEAD4),
    tertiaryVariant = Color(0xFF2DD4BF),
    error = Color(0xFFF87171),
    surface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFF49454F),
    background = Color(0xFF100F12),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onError = Color.Black,
    onSurface = Color(0xFFE6E1E5),
    onSurfaceVariant = Color(0xFFCAC4D0),
    onBackground = Color(0xFFE6E1E5),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F)
)

@Composable
fun CastleTheme(
    preferences: UserPreferences = UserPreferences(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isSystemDark = androidx.compose.material3.isSystemInDarkTheme()

    val colorScheme = when (preferences.themeMode) {
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.SYSTEM -> if (isSystemDark) DarkColorScheme else LightColorScheme
    }

    SideEffect {
        val window = (context as? android.app.Activity)?.window
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            val isDark = colorScheme == DarkColorScheme
            it.statusBarColor = if (isDark) Color(0xFF100F12).toArgb() else Color(0xFF4C1D95).toArgb()
            it.navigationBarColor = if (isDark) Color(0xFF100F12).toArgb() else Color(0xFFFFFFFF).toArgb()
            androidx.core.view.WindowCompat.getInsetsController(it, it.decorView)?.isAppearanceLightStatusBars = !isDark
            androidx.core.view.WindowCompat.getInsetsController(it, it.decorView)?.isAppearanceLightNavigationBars = !isDark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CastleTypography,
        shapes = CastleShapes,
        content = content
    )
}

object CastleTypography {
    val displayLarge = androidx.compose.material3.Typography.default.displayLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = -0.5.sp
    )
    val displayMedium = androidx.compose.material3.Typography.default.displayMedium.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.sp
    )
    val displaySmall = androidx.compose.material3.Typography.default.displaySmall.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.sp
    )
    val headlineLarge = androidx.compose.material3.Typography.default.headlineLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.sp
    )
    val headlineMedium = androidx.compose.material3.Typography.default.headlineMedium.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
        letterSpacing = 0.sp
    )
    val headlineSmall = androidx.compose.material3.Typography.default.headlineSmall.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
        letterSpacing = 0.sp
    )
    val titleLarge = androidx.compose.material3.Typography.default.titleLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.sp
    )
    val titleMedium = androidx.compose.material3.Typography.default.titleMedium.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.15.sp
    )
    val titleSmall = androidx.compose.material3.Typography.default.titleSmall.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.1.sp
    )
    val bodyLarge = androidx.compose.material3.Typography.default.bodyLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.5.sp
    )
    val bodyMedium = androidx.compose.material3.Typography.default.bodyMedium.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.25.sp
    )
    val bodySmall = androidx.compose.material3.Typography.default.bodySmall.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.4.sp
    )
    val labelLarge = androidx.compose.material3.Typography.default.labelLarge.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.1.sp
    )
    val labelMedium = androidx.compose.material3.Typography.default.labelMedium.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.5.sp
    )
    val labelSmall = androidx.compose.material3.Typography.default.labelSmall.copy(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.5.sp
    )
}

object CastleShapes {
    val extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
    val small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    val medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    val large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    val extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
}
