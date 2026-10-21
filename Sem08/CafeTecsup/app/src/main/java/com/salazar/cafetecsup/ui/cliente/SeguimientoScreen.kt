package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel

@Composable
fun SeguimientoScreen(
    pedidoId: Int,
    pedidosVm: PedidosClienteViewModel,
    onVolverMenu: () -> Unit,
    onNavegarPedidos: () -> Unit,
    onIrAPerfil: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val pedidoConDetalle by pedidosVm.obtenerPedido(pedidoId).collectAsState()
    val estadoActual = pedidoConDetalle?.pedido?.estado ?: "RECIBIDO"

    val pasos = listOf(
        Triple("RECIBIDO", "Recibido", "Tu pedido llegó a la cocina"),
        Triple("PREPARANDO", "Preparando", "Están cocinando tu pedido"),
        Triple("LISTO", "Listo", "Puedes pasar a recogerlo"),
        Triple("ENTREGADO", "Entregado", "¡Buen provecho!")
    )

    fun pasoAlcanzado(pasoEstado: String): Boolean {
        val orden = listOf("RECIBIDO", "PREPARANDO", "LISTO", "ENTREGADO")
        val idxActual = orden.indexOf(estadoActual)
        val idxPaso = orden.indexOf(pasoEstado)
        return idxPaso <= idxActual
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onVolverMenu() },
                    icon = { Text("🏠", fontSize = 18.sp) },
                    label = { Text("Menú", color = colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavegarPedidos() },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = colorScheme.secondary, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = colorScheme.secondary.copy(alpha = 0.15f))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onIrAPerfil,
                    icon = { Text("👤", fontSize = 18.sp) },
                    label = { Text("Perfil", color = colorScheme.onSurfaceVariant) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .clickable { onNavegarPedidos() }
                        .padding(end = 16.dp)
                )
                Text(
                    text = "Seguimiento",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.secondary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Pedido #${String.format("%04d", pedidoId)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Recojo a las ${pedidoConDetalle?.pedido?.horaRecojo ?: "10:30"}",
                            fontSize = 13.sp,
                            color = colorScheme.onSecondary.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "Tiempo estimado: 10 min",
                            fontSize = 13.sp,
                            color = colorScheme.onSecondary.copy(alpha = 0.85f)
                        )
                    }
                    Text("🕒", fontSize = 32.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                pasos.forEachIndexed { index, (codEstado, titulo, desc) ->
                    val alcanzado = pasoAlcanzado(codEstado)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (alcanzado) colorScheme.primary else colorScheme.outline
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (alcanzado) {
                                    Text("✓", color = colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                            if (index < pasos.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(44.dp)
                                        .background(
                                            if (pasoAlcanzado(pasos[index + 1].first)) colorScheme.primary else colorScheme.outline
                                        )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = titulo,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (alcanzado) colorScheme.onBackground else colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = desc,
                                fontSize = 13.sp,
                                color = colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            pedidoConDetalle?.let { pd ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val itemsText = pd.detalles.joinToString(" · ") { "${it.cantidad} x ${it.nombreProducto}" }
                        Text(
                            text = itemsText.ifBlank { "Productos del pedido" },
                            fontSize = 13.sp,
                            color = colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Total S/ ${String.format("%.2f", pd.pedido.total)}",
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.secondary,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
