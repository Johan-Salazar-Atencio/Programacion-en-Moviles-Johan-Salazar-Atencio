package com.salazar.centrotareas.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.salazar.centrotareas.model.Comparacion

@Composable
fun TablaComparacion(c: Comparacion) {
    Column {
        Fila("Modo", "Veces", "Último", "Promedio", negrita = true)
        Fila("Una por una", c.unaPorUna)
        Fila("A la vez", c.aLaVez)
        if (c.unaPorUna.isNotEmpty() && c.aLaVez.isNotEmpty()) {
            val dif = c.unaPorUna.average() - c.aLaVez.average()
            Text(
                "A la vez ahorra ${dif.toLong()} ms en promedio",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun Fila(modo: String, tiempos: List<Long>) = Fila(
    modo,
    tiempos.size.toString(),
    tiempos.lastOrNull()?.let { "$it ms" } ?: "-",
    if (tiempos.isEmpty()) "-" else "${tiempos.average().toLong()} ms"
)

@Composable
private fun Fila(a: String, b: String, c: String, d: String, negrita: Boolean = false) {
    Row(Modifier.fillMaxWidth()) {
        Celda(a, 1.4f, negrita)
        Celda(b, 0.8f, negrita)
        Celda(c, 1f, negrita)
        Celda(d, 1.1f, negrita)
    }
}

@Composable
private fun RowScope.Celda(texto: String, peso: Float, negrita: Boolean) {
    Text(
        texto,
        modifier = Modifier.weight(peso),
        fontWeight = if (negrita) FontWeight.Bold else FontWeight.Normal,
        style = MaterialTheme.typography.bodyMedium
    )
}
