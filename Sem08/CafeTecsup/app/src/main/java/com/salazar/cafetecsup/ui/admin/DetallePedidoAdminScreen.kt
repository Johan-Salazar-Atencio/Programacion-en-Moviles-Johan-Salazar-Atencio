package com.salazar.cafetecsup.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.model.EstadoPedido
import com.salazar.cafetecsup.ui.cliente.EstadoChip
import com.salazar.cafetecsup.viewmodel.PedidosAdminViewModel

@Composable
fun DetallePedidoAdminScreen(
    pedidoId: Int,
    vm: PedidosAdminViewModel,
    onVolver: () -> Unit
) {
    val pedidoConDetalle by vm.obtenerPedido(pedidoId).collectAsState()

    val pedido = pedidoConDetalle?.pedido
    val detalles = pedidoConDetalle?.detalles ?: emptyList()

    val estadoActual = try {
        EstadoPedido.valueOf(pedido?.estado ?: "RECIBIDO")
    } catch (e: Exception) {
        EstadoPedido.RECIBIDO
    }

    val siguienteTexto = when (estadoActual) {
        EstadoPedido.RECIBIDO -> "Pasar a Preparando"
        EstadoPedido.PREPARANDO -> "Pasar a Listo"
        EstadoPedido.LISTO -> "Pasar a Entregado"
        else -> null
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clickable { onVolver() }
                        .padding(end = 16.dp)
                )
                Text(
                    text = "Detalle del pedido",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) { paddingValues ->
        if (pedido == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF4F46E5))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Cabecera Pedido
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Pedido #${String.format("%04d", pedido.id)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                EstadoChip(estado = pedido.estado)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Hoy · Recojo ${pedido.horaRecojo}",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // 2. Info Cliente + Lista de productos y total
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Cliente",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Estudiante (ID #${pedido.clienteId})",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.surfaceVariant)

                            detalles.forEach { d ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = d.nombreProducto,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "x${d.cantidad}",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                    Text(
                                        text = "S/ ${String.format("%.2f", d.precioUnitario * d.cantidad)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.surfaceVariant)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(
                                    "S/ ${String.format("%.2f", pedido.total)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFF4F46E5)
                                )
                            }
                        }
                    }
                }

                // 3. Nota del cliente (Caja destacada amarilla)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Nota del cliente",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFFB45309)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (pedido.nota.isNotBlank()) pedido.nota else "Sin notas adicionales",
                                fontSize = 14.sp,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }

                // 4. Seccion Cambiar estado
                item {
                    Column {
                        Text(
                            text = "Cambiar estado",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                EstadoPedido.RECIBIDO,
                                EstadoPedido.PREPARANDO,
                                EstadoPedido.LISTO,
                                EstadoPedido.ENTREGADO
                            ).forEach { est ->
                                val esSel = est == estadoActual
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { vm.cambiarEstadoDirecto(pedido.id, est) },
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (esSel) Color(0xFF4F46E5) else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = when (est) {
                                                EstadoPedido.RECIBIDO -> "Recibido"
                                                EstadoPedido.PREPARANDO -> "Preparando"
                                                EstadoPedido.LISTO -> "Listo"
                                                EstadoPedido.ENTREGADO -> "Entregado"
                                                else -> est.name
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = if (esSel) FontWeight.Bold else FontWeight.Normal,
                                            color = if (esSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (siguienteTexto != null) {
                            Button(
                                onClick = { vm.avanzar(pedido) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5))
                            ) {
                                Text(siguienteTexto, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        if (estadoActual != EstadoPedido.CANCELADO && estadoActual != EstadoPedido.ENTREGADO) {
                            OutlinedButton(
                                onClick = {
                                    vm.cancelar(pedido)
                                    onVolver()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444))
                            ) {
                                Text("Cancelar pedido", fontSize = 15.sp, color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
