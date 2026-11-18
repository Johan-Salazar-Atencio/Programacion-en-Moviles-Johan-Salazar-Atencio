package com.salazar.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.salazar.centrotareas.model.CargaUi
import com.salazar.centrotareas.model.PrimosUi

private val VerdeFondo = Color(0xFFDDF3DD)
private val VerdeTexto = Color(0xFF1B5E20)

@Composable
private fun Recuadro(fondo: Color, contenido: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(fondo)
            .padding(12.dp)
    ) { contenido() }
}

@Composable
fun ResultadoPrimos(estado: PrimosUi) {
    when (estado) {
        is PrimosUi.Listo -> Recuadro(VerdeFondo) {
            Text("Primos: ${estado.cantidad}", color = VerdeTexto)
            Text("Tiempo: ${estado.milisegundos} ms", color = VerdeTexto)
        }
        PrimosUi.Cancelado -> Text("Cancelado", color = MaterialTheme.colorScheme.secondary)
        PrimosUi.Calculando -> Text("Calculando...")
        PrimosUi.Inicial -> Unit
    }
}

@Composable
fun ResultadoCarga(estado: CargaUi, onReintentar: () -> Unit) {
    when (estado) {
        is CargaUi.Exito -> Recuadro(VerdeFondo) {
            Text("Modo: ${estado.modo}", color = VerdeTexto)
            Text("Perfil: ${estado.perfil}", color = VerdeTexto)
            Text("Pedidos: ${estado.pedidos}", color = VerdeTexto)
            Text("Tiempo: ${estado.milisegundos} ms", color = VerdeTexto)
        }
        is CargaUi.Error -> Recuadro(MaterialTheme.colorScheme.errorContainer) {
            Text(estado.mensaje, color = MaterialTheme.colorScheme.onErrorContainer)
            TextButton(onClick = onReintentar) { Text("Reintentar") }
        }
        CargaUi.Cargando, CargaUi.Inicial -> Unit
    }
}
