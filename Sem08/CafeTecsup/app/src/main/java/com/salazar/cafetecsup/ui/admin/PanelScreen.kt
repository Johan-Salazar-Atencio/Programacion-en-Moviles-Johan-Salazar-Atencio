package com.salazar.cafetecsup.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.salazar.cafetecsup.viewmodel.ReporteDiario

data class ItemHoraGrafico(
    val hora: String,
    val cantidad: Int,
    val esDestacado: Boolean = false,
)

@Composable
fun PanelScreen(
    vm: PanelAdminViewModel,
    onNavegarPedidos: () -> Unit,
    onNavegarMenu: () -> Unit = {},
    onNavegarAjustes: () -> Unit = {},
    onCerrarSesion: () -> Unit,
    onVerDetalle: (Int) -> Unit = {}
) {
    val nuevos by vm.nuevosCount.collectAsState()
    val preparando by vm.preparandoCount.collectAsState()
    val listos by vm.listosCount.collectAsState()
    val ventasHoy by vm.ventasHoy.collectAsState()
    val nuevosPedidosLista by vm.nuevosPedidos.collectAsState()
    val reporteDiario by vm.reporteDiario.collectAsState()
    val pedidosPorHora by vm.pedidosPorHora.collectAsState()

    val datosGrafico = remember(pedidosPorHora) {
        if (pedidosPorHora.isEmpty()) {
            emptyList()
        } else {
            val porHora = pedidosPorHora.associate { it.hora to it.cantidad }
            val minHora = porHora.keys.min()
            val maxHora = porHora.keys.max()
            val maxCantidad = porHora.values.max()

            (minHora..maxHora).map { hora ->
                val cantidad = porHora[hora] ?: 0
                ItemHoraGrafico(
                    hora = "${hora}h",
                    cantidad = cantidad,
                    esDestacado = cantidad == maxCantidad && cantidad > 0
                )
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 8.dp) {
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
                    label = { Text("Pedidos", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavegarMenu() },
                    icon = { Text("☕", fontSize = 18.sp) },
                    label = { Text("Menú", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavegarAjustes() },
                    icon = { Text("⚙️", fontSize = 18.sp) },
                    label = { Text("Ajustes", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cabecera superior
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Panel de control", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Text("Cafetería · Hoy", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("🚪", fontSize = 22.sp, modifier = Modifier.clickable { onCerrarSesion() })
                }
            }

            // 1. 3 Contadores
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ContadorBox("Nuevos", nuevos, Color(0xFFDBEAFE), Color(0xFF1D4ED8), Modifier.weight(1f))
                    ContadorBox("Preparando", preparando, Color(0xFFFEF3C7), Color(0xFFD97706), Modifier.weight(1f))
                    ContadorBox("Listos", listos, Color(0xFFD1FAE5), Color(0xFF059669), Modifier.weight(1f))
                }
            }

            // 2. Tarjeta Ventas del Día
            item {
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
                            Text("S/ ${String.format("%.2f", ventasHoy)}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                        Text("21 pedidos", color = Color(0xFFC7D2FE), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // 3. Reporte Diario
            item {
                ReporteDiarioCard(reporte = reporteDiario)
            }

            // 4. Gráfico "Pedidos por hora"
            item {
                Column {
                    Text("Pedidos por hora", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(8.dp))
                    GraficoPedidosPorHora(datos = datosGrafico)
                }
            }

            // 5. Resumen Nuevos Pedidos
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nuevos pedidos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "Ver todos",
                        fontSize = 14.sp,
                        color = Color(0xFF4F46E5),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavegarPedidos() }
                    )
                }
            }

            if (nuevosPedidosLista.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay nuevos pedidos pendientes", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    }
                }
            } else {
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

private fun etiquetaMetodoPago(metodo: String): String = when {
    metodo.contains("caja", ignoreCase = true) || metodo.contains("efectivo", ignoreCase = true) -> "Efectivo"
    metodo.contains("yape", ignoreCase = true) && metodo.contains("plin", ignoreCase = true) -> "Yape / Plin"
    metodo.contains("yape", ignoreCase = true) -> "Yape"
    metodo.contains("plin", ignoreCase = true) -> "Plin"
    else -> metodo
}

private fun emojiMetodoPago(metodo: String): String = when {
    metodo.contains("caja", ignoreCase = true) || metodo.contains("efectivo", ignoreCase = true) -> "💵"
    else -> "📱"
}

@Composable
fun ReporteDiarioCard(reporte: ReporteDiario) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "📊 Reporte Diario",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🥇", fontSize = 22.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Producto más vendido", fontSize = 12.sp, color = colorScheme.onSurfaceVariant)
                    Text(
                        text = reporte.productoEstrella.ifBlank { "Sin ventas registradas" },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurface
                    )
                }
                if (reporte.cantidadEstrella > 0) {
                    Text(
                        text = "${reporte.cantidadEstrella} und.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.secondary
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = colorScheme.outline)

            Text("Desglose por método de pago", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(8.dp))

            if (reporte.pagos.isEmpty()) {
                Text("Sin pagos registrados hoy", fontSize = 13.sp, color = colorScheme.onSurfaceVariant)
            } else {
                reporte.pagos.forEach { pago ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${emojiMetodoPago(pago.metodo)} ${etiquetaMetodoPago(pago.metodo)}",
                            fontSize = 13.sp,
                            color = colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "S/ ${String.format("%.2f", pago.monto)}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = colorScheme.outline)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total del día", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = colorScheme.onSurface)
                Text(
                    text = "S/ ${String.format("%.2f", reporte.totalRecaudado)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun GraficoPedidosPorHora(datos: List<ItemHoraGrafico>) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        if (datos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin pedidos registrados hoy",
                    fontSize = 13.sp,
                    color = colorScheme.onSurfaceVariant
                )
            }
        } else {
            val maxCantidad = (datos.maxOfOrNull { it.cantidad } ?: 0).coerceAtLeast(1)
            val alturaMaxima = 96.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                datos.forEach { item ->
                    val fraccion = item.cantidad.toFloat() / maxCantidad
                    val alturaBarra = (alturaMaxima * fraccion).coerceAtLeast(4.dp)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "${item.cantidad}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height(alturaBarra)
                                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                .background(
                                    if (item.esDestacado) colorScheme.primary
                                    else colorScheme.primary.copy(alpha = 0.35f)
                                )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = item.hora,
                            fontSize = 12.sp,
                            color = colorScheme.onSurfaceVariant
                        )
                    }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("#${String.format("%04d", pedidoConDetalle.pedido.id)} · Estudiante", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                val itemsSummary = pedidoConDetalle.detalles.joinToString { "${it.cantidad}x ${it.nombreProducto}" }
                Text(itemsSummary.ifBlank { "Pedido de productos" }, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(
                onClick = onPreparar,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Preparar", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
