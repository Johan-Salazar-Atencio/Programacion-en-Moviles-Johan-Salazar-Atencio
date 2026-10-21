package com.salazar.cafetecsup.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import com.salazar.cafetecsup.model.EstadoPedido
import com.salazar.cafetecsup.ui.cliente.EstadoChip
import com.salazar.cafetecsup.viewmodel.PedidosAdminViewModel

@Composable
fun PedidosAdminScreen(
    vm: PedidosAdminViewModel,
    onVolverPanel: () -> Unit,
    onNavegarMenu: () -> Unit = {},
    onNavegarAjustes: () -> Unit = {},
    onVerDetalle: (Int) -> Unit = {}
) {
    val estadoSel by vm.estadoSeleccionado.collectAsState()
    val listaPedidos by vm.pedidos.collectAsState()

    val estados = listOf(
        EstadoPedido.RECIBIDO,
        EstadoPedido.PREPARANDO,
        EstadoPedido.LISTO,
        EstadoPedido.ENTREGADO
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onVolverPanel() },
                    icon = { Text("📊", fontSize = 18.sp) },
                    label = { Text("Panel", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = Color(0xFF4F46E5), fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFEEF2FF))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarMenu,
                    icon = { Text("☕", fontSize = 18.sp) },
                    label = { Text("Menú", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarAjustes,
                    icon = { Text("⚙️", fontSize = 18.sp) },
                    label = { Text("Ajustes", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable { onVolverPanel() }.padding(end = 16.dp)
                )
                Text("Pedidos de la Cafetería", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(estados) { estado ->
                    val esSel = estado == estadoSel
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { vm.seleccionarEstado(estado) },
                        color = if (esSel) Color(0xFF4F46E5) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = estado.name,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (esSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (esSel) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (listaPedidos.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No hay pedidos en estado ${estadoSel.name}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listaPedidos) { p ->
                        TarjetaPedidoAdmin(
                            pedidoConDetalle = p,
                            onClick = { onVerDetalle(p.pedido.id) },
                            onAvanzar = { vm.avanzar(p.pedido) },
                            onCancelar = { vm.cancelar(p.pedido) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaPedidoAdmin(
    pedidoConDetalle: PedidoConDetalle,
    onClick: () -> Unit,
    onAvanzar: () -> Unit,
    onCancelar: () -> Unit
) {
    val p = pedidoConDetalle.pedido
    val siguienteTexto = when (p.estado) {
        "RECIBIDO" -> "Pasar a Preparando"
        "PREPARANDO" -> "Pasar a Listo"
        "LISTO" -> "Pasar a Entregado"
        else -> null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("#${String.format("%04d", p.id)} · Estudiante", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                EstadoChip(estado = p.estado)
            }

            Spacer(modifier = Modifier.height(8.dp))

            val itemsText = pedidoConDetalle.detalles.joinToString("\n") { "${it.cantidad}x ${it.nombreProducto}" }
            Text(itemsText.ifBlank { "Sin detalle" }, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            if (p.nota.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Nota: ${p.nota}", fontSize = 12.sp, color = Color(0xFFD97706), fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Recojo: ${p.horaRecojo}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("S/ ${String.format("%.2f", p.total)}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF4F46E5))
            }

            if (siguienteTexto != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onAvanzar,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5))
                    ) {
                        Text(siguienteTexto, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onCancelar,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444))
                    ) {
                        Text("Cancelar", fontSize = 13.sp, color = Color(0xFFEF4444))
                    }
                }
            }
        }
    }
}
