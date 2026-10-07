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

const val COSTO_DELIVERY_SOPORTADO = 5.00

@Composable
fun DatosEntregaScreen(
    subtotal: Double,
    direccionInicial: String = "",
    referenciaInicial: String = "",
    onVolver: () -> Unit,
    onConfirmarPedido: (direccion: String, referencia: String, metodoPago: String, tipoEntrega: String, total: Double) -> Unit
) {
    val opcionesEntrega = listOf("Delivery a domicilio (S/ 5.00)", "Recojo en tienda (Gratis)")
    var opcionEntregaSeleccionada by remember { mutableStateOf(opcionesEntrega[0]) }

    val esDelivery = opcionEntregaSeleccionada.contains("Delivery")
    val costoEnvio = if (esDelivery) COSTO_DELIVERY_SOPORTADO else 0.00
    val montoTotalCalculado = subtotal + costoEnvio

    var direccion by remember { mutableStateOf(direccionInicial) }
    var referencia by remember { mutableStateOf(referenciaInicial) }

    var errorDireccion by remember { mutableStateOf(false) }
    var errorReferencia by remember { mutableStateOf(false) }

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

        // Selección de Método de Entrega
        Text(
            text = "Tipo de Entrega",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Column(Modifier.selectableGroup()) {
            opcionesEntrega.forEach { opcion ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (opcion == opcionEntregaSeleccionada),
                            onClick = {
                                opcionEntregaSeleccionada = opcion
                                errorDireccion = false
                                errorReferencia = false
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (opcion == opcionEntregaSeleccionada),
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

        Spacer(Modifier.height(16.dp))

        if (esDelivery) {
            Text(
                text = "Dirección de Envío",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))

            CampoTexto(
                etiqueta = "Dirección exacta",
                valor = direccion,
                onValorCambia = {
                    direccion = it
                    errorDireccion = false
                },
                placeholder = "Ej. Av. Primavera 123, Dpto 402",
                esError = errorDireccion,
                mensajeError = if (errorDireccion) "La dirección es obligatoria para Delivery" else null
            )

            Spacer(Modifier.height(12.dp))

            CampoTexto(
                etiqueta = "Referencia de ubicación",
                valor = referencia,
                onValorCambia = {
                    referencia = it
                    errorReferencia = false
                },
                placeholder = "Ej. Frente al parque, puerta verde",
                esError = errorReferencia,
                mensajeError = if (errorReferencia) "La referencia es obligatoria para Delivery" else null
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Punto de Recojo:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Bodega Principal - Av. Los Olivos 123, Lima",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Horario: Lunes a Sábado de 8:00 am a 8:00 pm",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

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
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal:", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "S/ %.2f".format(subtotal), style = MaterialTheme.typography.bodyMedium)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Costo de Envío:", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = if (costoEnvio == 0.0) "GRATIS" else "S/ %.2f".format(costoEnvio),
                        style = MaterialTheme.typography.bodyMedium,
                        color = VerdeBodega
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total a pagar:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "S/ %.2f".format(montoTotalCalculado),
                        style = MaterialTheme.typography.titleLarge,
                        color = VerdeBodega,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Confirmar Pedido",
            habilitado = true,
            onClick = {
                if (esDelivery) {
                    val esDireccionVacia = direccion.isBlank()
                    val esReferenciaVacia = referencia.isBlank()

                    errorDireccion = esDireccionVacia
                    errorReferencia = esReferenciaVacia

                    if (!esDireccionVacia && !esReferenciaVacia) {
                        onConfirmarPedido(
                            direccion,
                            referencia,
                            metodoPagoSeleccionado,
                            "Delivery",
                            montoTotalCalculado
                        )
                    }
                } else {
                    onConfirmarPedido(
                        "Bodega Principal - Av. Los Olivos 123",
                        "Recojo en tienda",
                        metodoPagoSeleccionado,
                        "Recojo en tienda",
                        montoTotalCalculado
                    )
                }
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
            subtotal = 24.10,
            onVolver = {},
            onConfirmarPedido = { _, _, _, _, _ -> }
        )
    }
}
