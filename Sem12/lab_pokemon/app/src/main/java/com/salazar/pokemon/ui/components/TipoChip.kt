package com.salazar.pokemon.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

private val ColoresTipo = mapOf(
    "fire" to Color(0xFFD9480F),
    "water" to Color(0xFF3B82F6),
    "grass" to Color(0xFF4CAF50),
    "electric" to Color(0xFFF5C518),
    "ice" to Color(0xFF7DD3FC),
    "fighting" to Color(0xFFB91C1C),
    "poison" to Color(0xFF9333EA),
    "ground" to Color(0xFFB7791F),
    "flying" to Color(0xFF93B4F5),
    "psychic" to Color(0xFFEC4899),
    "bug" to Color(0xFF84A316),
    "rock" to Color(0xFF8D7B52),
    "ghost" to Color(0xFF5B4B8A),
    "dragon" to Color(0xFF4F46E5),
    "dark" to Color(0xFF4B3B35),
    "steel" to Color(0xFF64748B),
    "fairy" to Color(0xFFF9A8D4),
    "normal" to Color(0xFF9CA3AF)
)

private val NombresTipo = mapOf(
    "fire" to "Fuego",
    "water" to "Agua",
    "grass" to "Planta",
    "electric" to "Eléctrico",
    "ice" to "Hielo",
    "fighting" to "Lucha",
    "poison" to "Veneno",
    "ground" to "Tierra",
    "flying" to "Volador",
    "psychic" to "Psíquico",
    "bug" to "Bicho",
    "rock" to "Roca",
    "ghost" to "Fantasma",
    "dragon" to "Dragón",
    "dark" to "Siniestro",
    "steel" to "Acero",
    "fairy" to "Hada",
    "normal" to "Normal"
)

@Composable
fun TipoChip(tipo: String, modifier: Modifier = Modifier) {
    val fondo = ColoresTipo[tipo] ?: Color(0xFF9CA3AF)
    val texto = if (fondo.luminance() > 0.3f) Color.Black else Color.White
    AssistChip(
        onClick = {},
        label = { Text(NombresTipo[tipo] ?: tipo.replaceFirstChar { it.uppercase() }) },
        colors = AssistChipDefaults.assistChipColors(containerColor = fondo, labelColor = texto),
        border = null,
        modifier = modifier
    )
}
