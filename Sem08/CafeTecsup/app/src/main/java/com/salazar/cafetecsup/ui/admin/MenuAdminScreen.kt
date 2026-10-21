package com.salazar.cafetecsup.ui.admin

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.ui.components.obtenerImagenProducto
import com.salazar.cafetecsup.viewmodel.MenuAdminViewModel

@Composable
fun MenuAdminScreen(
    vm: MenuAdminViewModel,
    onNavegarPanel: () -> Unit,
    onNavegarPedidos: () -> Unit,
    onNavegarMenu: () -> Unit = {},
    onNavegarAjustes: () -> Unit = {},
    onAgregarProducto: () -> Unit,
    onEditarProducto: (Int) -> Unit
) {
    val productos by vm.productos.collectAsState()
    var categoriaSel by remember { mutableStateOf("Todo") }
    val categorias = listOf("Todo", "Desayunos", "Almuerzos", "Bebidas")

    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    val productosFiltrados = remember(productos, categoriaSel) {
        if (categoriaSel == "Todo") productos
        else productos.filter { it.categoria.equals(categoriaSel, ignoreCase = true) }
    }

    productoAEliminar?.let { p ->
        AlertDialog(
            onDismissRequest = { productoAEliminar = null },
            title = { Text("Eliminar producto") },
            text = { Text("¿Estás seguro de que deseas eliminar \"${p.nombre}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        vm.eliminar(p)
                        productoAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { productoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarProducto,
                containerColor = Color(0xFF4F46E5),
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("➕", fontSize = 22.sp)
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarPanel,
                    icon = { Text("📊", fontSize = 18.sp) },
                    label = { Text("Panel", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarPedidos,
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = onNavegarMenu,
                    icon = { Text("☕", fontSize = 18.sp) },
                    label = { Text("Menú", color = Color(0xFF4F46E5), fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFEEF2FF))
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
                    modifier = Modifier.clickable { onNavegarPanel() }.padding(end = 16.dp)
                )
                Text("Gestión del menú", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categorias) { cat ->
                    val esSel = cat == categoriaSel
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { categoriaSel = cat },
                        color = if (esSel) Color(0xFF4F46E5) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (esSel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (esSel) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (productosFiltrados.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No hay productos en esta categoría", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(productosFiltrados) { p ->
                        ItemProductoAdmin(
                            producto = p,
                            onToggleDisponibilidad = { vm.alternarDisponibilidad(p) },
                            onEditar = { onEditarProducto(p.id) },
                            onEliminar = { productoAEliminar = p }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemProductoAdmin(
    producto: Producto,
    onToggleDisponibilidad: () -> Unit,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFEF3C7)),
                contentAlignment = Alignment.Center
            ) {
                if (producto.icono.startsWith("content://") || producto.icono.startsWith("file://")) {
                    AsyncImage(
                        model = producto.icono,
                        contentDescription = producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = obtenerImagenProducto(producto.nombre)),
                        contentDescription = producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                Text("${producto.categoria} · S/ ${String.format("%.2f", producto.precio)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    color = if (producto.disponible) Color(0xFFD1FAE5) else Color(0xFFFEE2E2),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { onToggleDisponibilidad() }
                ) {
                    Text(
                        text = if (producto.disponible) "Disponible" else "Agotado",
                        color = if (producto.disponible) Color(0xFF059669) else Color(0xFFDC2626),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            IconButton(onClick = onEditar) {
                Text("✏️", fontSize = 18.sp)
            }

            IconButton(onClick = onEliminar) {
                Text("🗑️", fontSize = 18.sp)
            }
        }
    }
}
