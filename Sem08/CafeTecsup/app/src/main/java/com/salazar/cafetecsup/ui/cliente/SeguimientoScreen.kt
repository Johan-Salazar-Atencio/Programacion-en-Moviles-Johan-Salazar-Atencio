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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel

@Composable
fun SeguimientoScreen(
    pedidoId: Int,
    pedidosVm: PedidosClienteViewModel,
    onVolverMenu: () -> Unit,
    onNavegarPedidos: () -> Unit
) {
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
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onVolverMenu() },
                    icon = { Text("🏠", fontSize = 18.sp) },
                    label = { Text("Menú", color = Color(0xFF9CA3AF)) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavegarPedidos() },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = Color(0xFFEA580C), fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFFFEDD5))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Text("👤", fontSize = 18.sp) },
                    label = { Text("Perfil", color = Color(0xFF9CA3AF)) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
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
                    modifier = Modifier
                        .clickable { onNavegarPedidos() }
                        .padding(end = 16.dp)
                )
                Text(
                    text = "Seguimiento",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEA580C))
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
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Recojo a las ${pedidoConDetalle?.pedido?.horaRecojo ?: "10:30"}",
                            fontSize = 13.sp,
                            color = Color(0xFFFFEDD5)
                        )
                        Text(
                            text = "Tiempo estimado: 10 min",
                            fontSize = 13.sp,
                            color = Color(0xFFFFEDD5)
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
                                        if (alcanzado) Color(0xFF10B981) else Color(0xFFE5E7EB)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (alcanzado) {
                                    Text("✓", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                            if (index < pasos.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(44.dp)
                                        .background(
                                            if (pasoAlcanzado(pasos[index + 1].first)) Color(0xFF10B981) else Color(0xFFE5E7EB)
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
                                color = if (alcanzado) Color(0xFF111827) else Color(0xFF9CA3AF)
                            )
                            Text(
                                text = desc,
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
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
                    colors = CardDefaults.cardColors(containerColor = Color.White)
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
                            color = Color(0xFF4B5563),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Total S/ ${String.format("%.2f", pd.pedido.total)}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEA580C),
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}