package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.ui.components.obtenerImagenProducto
import com.salazar.cafetecsup.viewmodel.MenuViewModel
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel
import com.salazar.cafetecsup.viewmodel.SesionViewModel

@Composable
fun ConfirmarPedidoScreen(
    menuVm: MenuViewModel,
    pedidosVm: PedidosClienteViewModel,
    viewModel: SesionViewModel,
    onVolverMenu: () -> Unit,
    onPedidoConfirmado: (Long) -> Unit,
    onNavegarPedidos: () -> Unit,
    onIrAPerfil: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val carrito by menuVm.carrito.collectAsState()
    val recibirPedidos by viewModel.recibirPedidos.collectAsState()
    val subtotal = menuVm.total()
    val cerrado = !recibirPedidos

    var horaSeleccionada by remember { mutableStateOf("10:30") }
    val horasDisponibles = listOf("Ahora", "10:30", "11:00", "11:30")

    var metodoPagoSeleccionado by remember { mutableStateOf("Pagar en caja") }
    val metodosPago = listOf("Pagar en caja", "Yape / Plin")

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onVolverMenu() },
                    icon = { Text("🏠", fontSize = 18.sp) },
                    label = { Text("Menú", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavegarPedidos() },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onIrAPerfil,
                    icon = { Text("👤", fontSize = 18.sp) },
                    label = { Text("Perfil", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Cabecera superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable { onVolverMenu() }
                        .padding(end = 16.dp)
                )
                Text(
                    text = "Confirmar pedido",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (cerrado) {
                Surface(
                    color = colorScheme.error.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Cafetería cerrada",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "En este momento no estamos recibiendo nuevos pedidos.",
                            fontSize = 13.sp,
                            color = colorScheme.error.copy(alpha = 0.85f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Tarjeta de resumen de ítems
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            carrito.forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFFFEF3C7)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = obtenerImagenProducto(item.producto.nombre)),
                                            contentDescription = item.producto.nombre,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.producto.nombre,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "x${item.cantidad}",
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        if (item.nota.isNotBlank()) {
                                            Text(
                                                text = "Nota: ${item.nota}",
                                                fontSize = 12.sp,
                                                color = Color(0xFFD97706),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    Text(
                                        text = "S/ ${String.format("%.2f", item.producto.precio * item.cantidad)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // 2. Hora de recojo
                item {
                    Column {
                        Text(
                            text = "Hora de recojo",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            horasDisponibles.forEach { hora ->
                                val esSel = hora == horaSeleccionada
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable { horaSeleccionada = hora },
                                    color = if (esSel) Color(0xFFEA580C) else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = hora,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        color = if (esSel) colorScheme.onSecondary else colorScheme.onSurfaceVariant,
                                        fontWeight = if (esSel) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // 3. Forma de pago
                item {
                    Column {
                        Text(
                            text = "Forma de pago",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            metodosPago.forEach { metodo ->
                                val esSel = metodo == metodoPagoSeleccionado
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { metodoPagoSeleccionado = metodo },
                                    color = if (esSel) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
                                    border = if (esSel) androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFEA580C)) else null
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = metodo,
                                            color = if (esSel) Color(0xFFEA580C) else MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontWeight = if (esSel) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 4. Detalle de montos
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Subtotal", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("S/ ${String.format("%.2f", subtotal)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Servicio", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("S/ 0.00", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(
                                    "S/ ${String.format("%.2f", subtotal)}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFFEA580C)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Confirmar
            Button(
                onClick = {
                    val notaCombinada = carrito
                        .filter { it.nota.isNotBlank() }
                        .joinToString("; ") { item ->
                            if (carrito.size > 1) "${item.producto.nombre}: ${item.nota}" else item.nota
                        }

                    pedidosVm.confirmar(
                        usuarioId = 1,
                        items = carrito,
                        horaRecojo = horaSeleccionada,
                        pago = metodoPagoSeleccionado,
                        nota = notaCombinada
                    ) { nuevoId ->
                        menuVm.vaciar()
                        onPedidoConfirmado(nuevoId)
                    }
                },
                enabled = carrito.isNotEmpty() && recibirPedidos,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondary,
                    disabledContainerColor = colorScheme.onSurface.copy(alpha = 0.38f)
                )
            ) {
                Text("Confirmar pedido", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = colorScheme.onSecondary)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Seguir pidiendo
            OutlinedButton(
                onClick = { onVolverMenu() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEA580C))
            ) {
                Text("Seguir pidiendo", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFEA580C))
            }
        }
    }
}
