package com.salazar.cafetecsup.ui.cliente

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
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel

@Composable
fun MisPedidosScreen(
    pedidosVm: PedidosClienteViewModel,
    onVolverMenu: () -> Unit,
    onVerSeguimiento: (Int) -> Unit,
    onIrAPerfil: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val todosLosPedidos by pedidosVm.todosLosPedidos.collectAsState()
    var tabSeleccionada by remember { mutableStateOf(0) }

    val pedidosActivos = todosLosPedidos.filter {
        it.pedido.estado in listOf("RECIBIDO", "PREPARANDO", "LISTO")
    }

    val pedidosHistorial = todosLosPedidos.filter {
        it.pedido.estado in listOf("ENTREGADO", "CANCELADO")
    }

    val listaMostrar = if (tabSeleccionada == 0) pedidosActivos else pedidosHistorial

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
                    onClick = { },
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
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .clickable { onVolverMenu() }
                        .padding(end = 16.dp)
                )
                Text(
                    text = "Mis pedidos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp)),
                color = colorScheme.surfaceVariant
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { tabSeleccionada = 0 },
                        color = if (tabSeleccionada == 0) colorScheme.surface else Color.Transparent
                    ) {
                        Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Activos",
                                fontWeight = if (tabSeleccionada == 0) FontWeight.Bold else FontWeight.Normal,
                                color = if (tabSeleccionada == 0) colorScheme.secondary else colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { tabSeleccionada = 1 },
                        color = if (tabSeleccionada == 1) colorScheme.surface else Color.Transparent
                    ) {
                        Box(modifier = Modifier.padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Historial",
                                fontWeight = if (tabSeleccionada == 1) FontWeight.Bold else FontWeight.Normal,
                                color = if (tabSeleccionada == 1) colorScheme.secondary else colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (listaMostrar.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (tabSeleccionada == 0) "No tienes pedidos activos" else "No tienes historial de pedidos",
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listaMostrar) { pedidoConDetalle ->
                        TarjetaPedido(
                            pedidoConDetalle = pedidoConDetalle,
                            onClick = { onVerSeguimiento(pedidoConDetalle.pedido.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaPedido(
    pedidoConDetalle: PedidoConDetalle,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val pedido = pedidoConDetalle.pedido

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pedido #${String.format("%04d", pedido.id)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorScheme.onSurface
                )
                Text("›", fontSize = 20.sp, color = colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hoy ${pedido.horaRecojo}",
                fontSize = 12.sp,
                color = colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                EstadoChip(estado = pedido.estado)
                Text(
                    text = "S/ ${String.format("%.2f", pedido.total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun EstadoChip(estado: String) {
    val colorScheme = MaterialTheme.colorScheme
    val (fondo, textoColor) = when (estado) {
        "RECIBIDO" -> Pair(Color(0xFFFEF3C7), Color(0xFFD97706))
        "PREPARANDO" -> Pair(Color(0xFFFDE68A), Color(0xFFB45309))
        "LISTO" -> Pair(Color(0xFFD1FAE5), Color(0xFF059669))
        "ENTREGADO" -> Pair(colorScheme.surfaceVariant, colorScheme.onSurfaceVariant)
        "CANCELADO" -> Pair(colorScheme.error.copy(alpha = 0.15f), colorScheme.error)
        else -> Pair(colorScheme.surfaceVariant, colorScheme.onSurfaceVariant)
    }

    Surface(
        color = fondo,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = estado,
            color = textoColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
