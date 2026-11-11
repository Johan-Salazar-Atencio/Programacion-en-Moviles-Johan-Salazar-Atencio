package com.salazar.pokemon.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val EsquemaClaro = lightColorScheme(
    primary = FuegoPrimary,
    onPrimary = FuegoOnPrimary,
    primaryContainer = FuegoPrimaryContainer,
    onPrimaryContainer = FuegoOnPrimaryContainer,
    secondary = FuegoSecondary,
    secondaryContainer = FuegoSecondaryContainer,
    onSecondaryContainer = FuegoOnSecondaryContainer,
    tertiary = FuegoTertiary,
    tertiaryContainer = FuegoTertiaryContainer,
    onTertiaryContainer = FuegoOnTertiaryContainer,
    background = FuegoSurface,
    onBackground = FuegoOnSurface,
    surface = FuegoSurface,
    onSurface = FuegoOnSurface,
    surfaceVariant = FuegoSurfaceVariant,
    onSurfaceVariant = FuegoOnSurfaceVariant,
    outline = FuegoOutline,
    outlineVariant = FuegoOutlineVariant,
    surfaceContainerLowest = FuegoContainerLowest,
    surfaceContainerLow = FuegoContainerLow,
    surfaceContainer = FuegoContainer,
    surfaceContainerHigh = FuegoContainerHigh,
    surfaceContainerHighest = FuegoContainerHighest
)

private val EsquemaOscuro = darkColorScheme(
    primary = BrasaPrimary,
    onPrimary = BrasaOnPrimary,
    primaryContainer = BrasaPrimaryContainer,
    onPrimaryContainer = BrasaOnPrimaryContainer,
    secondary = BrasaSecondary,
    onSecondary = BrasaOnSecondary,
    secondaryContainer = BrasaSecondaryContainer,
    onSecondaryContainer = BrasaOnSecondaryContainer,
    tertiary = BrasaTertiary,
    onTertiary = BrasaOnTertiary,
    tertiaryContainer = BrasaTertiaryContainer,
    onTertiaryContainer = BrasaOnTertiaryContainer,
    background = BrasaSurface,
    onBackground = BrasaOnSurface,
    surface = BrasaSurface,
    onSurface = BrasaOnSurface,
    surfaceVariant = BrasaSurfaceVariant,
    onSurfaceVariant = BrasaOnSurfaceVariant,
    outline = BrasaOutline,
    outlineVariant = BrasaOutlineVariant,
    surfaceContainerLowest = BrasaContainerLowest,
    surfaceContainerLow = BrasaContainerLow,
    surfaceContainer = BrasaContainer,
    surfaceContainerHigh = BrasaContainerHigh,
    surfaceContainerHighest = BrasaContainerHighest
)

private val Formas = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun LabpokemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorDinamico: Boolean = false,
    content: @Composable () -> Unit
) {
    val esquema = when {
        colorDinamico && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }

    MaterialTheme(
        colorScheme = esquema,
        typography = Tipografia,
        shapes = Formas,
        content = content
    )
}
