package com.salazar.cafetecsup.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.salazar.cafetecsup.viewmodel.PanelAdminViewModel

data class ItemHoraGrafico(
    val hora: String,
    val cantidad: Int,
    val esDestacado: Boolean = false,
)

@Composable
fun PanelScreen(
    vm: PanelAdminViewModel,
    onNavegarPedidos: () -> Unit,
    onCerrarSesion: () -> Unit,
    onVerDetalle: (Int) -> Unit = {}
) {
    val nuevos by vm.nuevosCount.collectAsState()
    val preparando by vm.preparandoCount.collectAsState()
    val listos by vm.listosCount.collectAsState()
    val ventasHoy by vm.ventasHoy.collectAsState()
    val nuevosPedidosLista by vm.nuevosPedidos.collectAsState()

    val datosGrafico = remember {
        listOf(
            ItemHoraGrafico("8h", 3),
            ItemHoraGrafico("9h", 6),
            ItemHoraGrafico("10h", 9, esDestacado = true),
            ItemHoraGrafico("11h", 5),
            ItemHoraGrafico("12h", 7)
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Text("📊", fontSize = 18.sp) },
                    label = { Text("Panel", color = Color(0xFF4F46E5), fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFEEF2FF))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavegarPedidos() },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = Color(0xFF9CA3AF)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Text("☕", fontSize = 18.sp) },
                    label = { Text("Menú", color = Color(0xFF9CA3AF)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Text("⚙️", fontSize = 18.sp) },
                    label = { Text("Ajustes", color = Color(0xFF9CA3AF)) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Cabecera superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Panel de control", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                    Text("Cafetería · Hoy", fontSize = 13.sp, color = Color(0xFF6B7280))
                }
                Text("🚪", fontSize = 22.sp, modifier = Modifier.clickable { onCerrarSesion() })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. 3 Contadores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ContadorBox("Nuevos", nuevos, Color(0xFFDBEAFE), Color(0xFF1D4ED8), Modifier.weight(1f))
                ContadorBox("Preparando", preparando, Color(0xFFFEF3C7), Color(0xFFD97706), Modifier.weight(1f))
                ContadorBox("Listos", listos, Color(0xFFD1FAE5), Color(0xFF059669), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Tarjeta Ventas del Día
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4F46E5))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Ventas del día", color = Color(0xFFC7D2FE), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("S/ ${String.format("%.2f", ventasHoy)}", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Text("21 pedidos", color = Color(0xFFC7D2FE), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Gráfico "Pedidos por hora"
            Text("Pedidos por hora", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
            Spacer(modifier = Modifier.height(8.dp))
            GraficoPedidosPorHora(datos = datosGrafico)

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Resumen Nuevos Pedidos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Nuevos pedidos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                Text(
                    "Ver todos",
                    fontSize = 14.sp,
                    color = Color(0xFF4F46E5),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavegarPedidos() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (nuevosPedidosLista.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No hay nuevos pedidos pendientes", color = Color(0xFF9CA3AF), fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(nuevosPedidosLista) { p ->
                        ItemResumenPedido(
                            pedidoConDetalle = p,
                            onClick = { onVerDetalle(p.pedido.id) },
                            onPreparar = { vm.pasarAPreparando(p.pedido.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GraficoPedidosPorHora(datos: List<ItemHoraGrafico>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            datos.forEach { item ->
                val alturaBarra = (item.cantidad * 8).dp

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "${item.cantidad}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4B5563)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .height(alturaBarra)
                            .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(
                                if (item.esDestacado) Color(0xFF4F46E5) else Color(0xFFC7D2FE)
                            )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.hora,
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }
            }
        }
    }
}

@Composable
fun ContadorBox(label: String, count: Int, fondo: Color, textoColor: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = fondo)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$count", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textoColor)
            Spacer(modifier = Modifier.height(2.dp))
            Text(label, fontSize = 12.sp, color = textoColor, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ItemResumenPedido(
    pedidoConDetalle: PedidoConDetalle,
    onClick: () -> Unit,
    onPreparar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("#${String.format("%04d", pedidoConDetalle.pedido.id)} · Estudiante", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                val itemsSummary = pedidoConDetalle.detalles.joinToString { "${it.cantidad}x ${it.nombreProducto}" }
                Text(itemsSummary.ifBlank { "Pedido de productos" }, fontSize = 12.sp, color = Color(0xFF6B7280))
            }
            Button(
                onClick = onPreparar,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Preparar", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}