package com.tecsup.mibodega.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BodegaColorScheme = lightColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    primaryContainer = VerdeSuave,
    onPrimaryContainer = VerdeOscuro,
    secondary = AzulEnlace,
    background = FondoPantalla,
    onBackground = AzulTexto,
    surface = Blanco,
    onSurface = AzulTexto,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,
    outline = GrisBorde,
    error = RojoPrecio
)

@Composable
fun BodegaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BodegaColorScheme,
        typography = BodegaTypography,
        content = content
    )
}
