package com.salazar.cafetecsup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6366F1), // Indigo suave para Admin
    onPrimary = Color.White,
    secondary = Color(0xFFF97316), // Naranja suave para Cliente
    onSecondary = Color.White,
    background = Color(0xFF121212), // Fondo principal oscuro
    onBackground = Color(0xFFF9FAFB), // Texto sobre fondo
    surface = Color(0xFF1E1E1E), // Fondo de tarjetas/cards en oscuro
    onSurface = Color(0xFFF9FAFB), // Texto sobre tarjetas
    surfaceVariant = Color(0xFF27272A), // Variantes de tarjeta
    onSurfaceVariant = Color(0xFFD4D4D8),
    outline = Color(0xFF3F3F46)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4F46E5),
    onPrimary = Color.White,
    secondary = Color(0xFFEA580C),
    onSecondary = Color.White,
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF111827),
    surface = Color.White,
    onSurface = Color(0xFF111827),
    surfaceVariant = Color(0xFFF3F4F6),
    onSurfaceVariant = Color(0xFF6B7280),
    outline = Color(0xFFE5E7EB)
)

@Composable
fun CafetecsupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
