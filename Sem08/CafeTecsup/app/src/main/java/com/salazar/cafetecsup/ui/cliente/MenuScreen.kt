package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.salazar.cafetecsup.ui.components.ProductoCard
import com.salazar.cafetecsup.viewmodel.MenuViewModel

@Composable
fun MenuScreen(
    vm: MenuViewModel,
    onProducto: (Int) -> Unit
) {
    val productos by vm.productos.collectAsState()
    val carrito by vm.carrito.collectAsState()
    val totalItems = carrito.sumOf { it.cantidad }

    var textoBusqueda by remember { mutableStateOf("") }

    var categoriaSeleccionada by remember { mutableStateOf("Todo") }
    val categorias = listOf("Todo", "Desayunos", "Almuerzos", "Bebidas")

    val productosFiltrados = remember(productos, categoriaSeleccionada) {
        if (categoriaSeleccionada == "Todo") {
            productos
        } else {
            productos.filter { p ->
                when (categoriaSeleccionada) {
                    "Desayunos" -> p.categoria.contains("desayuno", ignoreCase = true) ||
                            p.nombre.contains("empanada", ignoreCase = true) ||
                            p.nombre.contains("sándwich", ignoreCase = true) ||
                            p.nombre.contains("sandwich", ignoreCase = true)

                    "Almuerzos" -> p.categoria.contains("almuerzo", ignoreCase = true) ||
                            p.nombre.contains("menú", ignoreCase = true) ||
                            p.nombre.contains("menu", ignoreCase = true)

                    "Bebidas" -> p.categoria.contains("bebida", ignoreCase = true) ||
                            p.nombre.contains("café", ignoreCase = true) ||
                            p.nombre.contains("cafe", ignoreCase = true) ||
                            p.nombre.contains("chicha", ignoreCase = true) ||
                            p.nombre.contains("jugo", ignoreCase = true)

                    else -> true
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Pantalla actual */ },
                    icon = { Text("🏠", fontSize = 18.sp) },
                    label = { Text("Menú", color = Color(0xFFEA580C), fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color(0xFFFFEDD5)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* Conexión en paso de Pedidos */ },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = Color(0xFF9CA3AF)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* Conexión en paso de Perfil */ },
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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "¡Hola, Johan!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "¿Qué se te antoja hoy?",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3F4F6)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🛍️", fontSize = 20.sp)
                    if (totalItems > 0) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEA580C))
                                .align(Alignment.TopEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$totalItems",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar en el menú...", color = Color(0xFF9CA3AF)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFEA580C),
                    unfocusedBorderColor = Color(0xFFE5E7EB)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { cat ->
                    val esSeleccionado = cat == categoriaSeleccionada
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { categoriaSeleccionada = cat },
                        color = if (esSeleccionado) Color(0xFFEA580C) else Color(0xFFF3F4F6)
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (esSeleccionado) Color.White else Color(0xFF4B5563),
                            fontWeight = if (esSeleccionado) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Populares hoy",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos en esta categoría",
                        color = Color(0xFF9CA3AF),
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(productosFiltrados) { p ->
                        ProductoCard(
                            producto = p,
                            onClick = { onProducto(p.id) }
                        )
                    }
                }
            }
        }
    }
}