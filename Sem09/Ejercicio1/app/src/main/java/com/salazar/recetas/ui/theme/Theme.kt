package com.salazar.recetas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaCocina = lightColorScheme(
    primary = Naranja,
    onPrimary = Color.White,
    primaryContainer = NaranjaClaro,
    onPrimaryContainer = Cafe,
    secondary = Verde,
    onSecondary = Color.White,
    secondaryContainer = VerdeClaro,
    onSecondaryContainer = Cafe,
    background = Crema,
    onBackground = Cafe,
    surface = Color.White,
    onSurface = Cafe
)

@Composable
fun RecetasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaCocina,
        typography = Typography,
        content = content
    )
}
