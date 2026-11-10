package com.reabuilt.a94761525castlecustom.ui.theme

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.reabuilt.a94761525castlecustom.data.model.ThemeMode
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B21A8),
    secondary = Color(0xFFB45309),
    tertiary = Color(0xFF0F766E),
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
    secondary = Color(0xFFFCD34D),
    tertiary = Color(0xFF5EEAD4),
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
            WindowCompat.getInsetsController(it, it.decorView)?.isAppearanceLightStatusBars = !isDark
            WindowCompat.getInsetsController(it, it.decorView)?.isAppearanceLightNavigationBars = !isDark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val Typography = androidx.compose.material3.Typography(
    displayLarge = androidx.compose.material3.Typography.default.displayLarge.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = androidx.compose.material3.Typography.default.displayMedium.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    ),
    displaySmall = androidx.compose.material3.Typography.default.displaySmall.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    ),
    headlineLarge = androidx.compose.material3.Typography.default.headlineLarge.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    ),
    headlineMedium = androidx.compose.material3.Typography.default.headlineMedium.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp
    ),
    headlineSmall = androidx.compose.material3.Typography.default.headlineSmall.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp
    ),
    titleLarge = androidx.compose.material3.Typography.default.titleLarge.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp
    ),
    titleMedium = androidx.compose.material3.Typography.default.titleMedium.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp
    ),
    titleSmall = androidx.compose.material3.Typography.default.titleSmall.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = androidx.compose.material3.Typography.default.bodyLarge.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = androidx.compose.material3.Typography.default.bodyMedium.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp
    ),
    bodySmall = androidx.compose.material3.Typography.default.bodySmall.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.4.sp
    ),
    labelLarge = androidx.compose.material3.Typography.default.labelLarge.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    ),
    labelMedium = androidx.compose.material3.Typography.default.labelMedium.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp
    ),
    labelSmall = androidx.compose.material3.Typography.default.labelSmall.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp
    )
)
