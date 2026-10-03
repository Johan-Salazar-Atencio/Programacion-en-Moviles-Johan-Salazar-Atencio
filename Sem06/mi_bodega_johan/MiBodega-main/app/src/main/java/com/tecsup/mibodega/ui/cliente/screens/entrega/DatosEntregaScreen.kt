package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.QrCodeScanner
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 06: Datos de entrega y Checkout.
 */
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

    val opcionesPago = listOf(
        Triple("Efectivo", "Paga al recibir la entrega", Icons.Outlined.Payments),
        Triple("Yape / Plin", "Transferencia digital sin contacto", Icons.Outlined.QrCodeScanner),
        Triple("Tarjeta de Débito/Crédito", "Visa, Mastercard o AMEX", Icons.Outlined.CreditCard)
    )
    var metodoPagoSeleccionado by remember { mutableStateOf(opcionesPago[0].first) }

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
                text = "Datos de Entrega y Pago",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Dirección de Envío",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Dirección exacta",
            valor = direccion,
            onValorCambia = { direccion = it },
            placeholder = "Ej. Av. Primavera 123, Dpto 402",
            icono = Icons.Outlined.LocationOn
        )

        Spacer(Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Referencia de ubicación",
            valor = referencia,
            onValorCambia = { referencia = it },
            placeholder = "Ej. Frente al parque, puerta verde",
            icono = Icons.Outlined.Info
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Método de Pago",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            opcionesPago.forEach { (nombre, descripcion, icono) ->
                TarjetaMetodoPago(
                    nombre = nombre,
                    descripcion = descripcion,
                    icono = icono,
                    seleccionado = (nombre == metodoPagoSeleccionado),
                    onClick = { metodoPagoSeleccionado = nombre }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monto Total a pagar:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
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
            icono = Icons.Default.CheckCircle,
            habilitado = direccion.isNotBlank(),
            onClick = {
                onConfirmarPedido(direccion, referencia, metodoPagoSeleccionado)
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TarjetaMetodoPago(
    nombre: String,
    descripcion: String,
    icono: ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
        label = "MetodoPagoBorder"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(if (seleccionado) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (seleccionado) 3.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = seleccionado,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = VerdeBodega)
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = if (seleccionado) VerdeBodega else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
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
