package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

//Pantalla 06 Datos de entega dodne se ve metodos de pago
@Composable
fun DatosEntregaScreen(
    montoTotal: Double,
    direccionInicial: String = "",
    referenciaInicial: String = "",
    onVolver: () -> Unit,
    onConfirmarPedido: (direccion: String, referencia: String, metodoPago: String) -> Unit
) {
    var direccion by remember { mutableStateOf(direccionInicial) }
    var referencia by remember { mutableStateOf(referenciaInicial) }

    val opcionesPago = listOf("Efectivo", "Yape / Plin", "Tarjeta de Débito/Crédito")
    var metodoPagoSeleccionado by remember { mutableStateOf(opcionesPago[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Datos de entrega",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Dirección de Envío",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Dirección exacta",
            valor = direccion,
            onValorCambia = { direccion = it },
            placeholder = "Ej. Av. Primavera 123, Dpto 402"
        )

        Spacer(Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Referencia de ubicación",
            valor = referencia,
            onValorCambia = { referencia = it },
            placeholder = "Ej. Frente al parque, puerta verde"
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Método de Pago",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Column(Modifier.selectableGroup()) {
            opcionesPago.forEach { opcion ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (opcion == metodoPagoSeleccionado),
                            onClick = { metodoPagoSeleccionado = opcion },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (opcion == metodoPagoSeleccionado),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(selectedColor = VerdeBodega)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = opcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total a pagar:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "S/ %.2f".format(montoTotal),
                    style = MaterialTheme.typography.titleLarge,
                    color = VerdeBodega,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Confirmar Pedido",
            habilitado = direccion.isNotBlank(),
            onClick = {
                onConfirmarPedido(direccion, referencia, metodoPagoSeleccionado)
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(
            montoTotal = 28.10,
            onVolver = {},
            onConfirmarPedido = { _, _, _ -> }
        )
    }
}