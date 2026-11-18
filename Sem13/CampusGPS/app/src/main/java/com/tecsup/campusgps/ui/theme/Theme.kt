package com.tecsup.campusgps.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Guinda,
    onPrimary = Color.White,
    primaryContainer = GuindaContenedor,
    onPrimaryContainer = GuindaOscura,
    secondary = Color(0xFF75565C),
    secondaryContainer = Color(0xFFFFD9E0),
    onSecondaryContainer = Color(0xFF2B151A),
    background = Color(0xFFFFFBFF),
    surface = Color(0xFFFFFBFF)
)

private val EsquemaOscuro = darkColorScheme(
    primary = GuindaClara,
    onPrimary = GuindaProfunda,
    primaryContainer = Guinda,
    onPrimaryContainer = GuindaContenedor,
    secondary = Color(0xFFE3BDC4),
    secondaryContainer = Color(0xFF5B3F45),
    onSecondaryContainer = Color(0xFFFFD9E0),
    background = Color(0xFF201A1B),
    surface = Color(0xFF201A1B)
)

@Composable
fun CampusGPSTheme(
    oscuro: Boolean = isSystemInDarkTheme(),
    contenido: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (oscuro) EsquemaOscuro else EsquemaClaro,
        content = contenido
    )
}
