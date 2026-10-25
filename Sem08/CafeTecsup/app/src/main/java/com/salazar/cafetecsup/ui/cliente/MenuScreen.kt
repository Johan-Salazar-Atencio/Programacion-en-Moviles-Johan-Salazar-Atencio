package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.ui.components.ProductoCard
import com.salazar.cafetecsup.viewmodel.MenuViewModel

@Composable
fun MenuScreen(
    vm: MenuViewModel,
    onProducto: (Int) -> Unit,
    onIrAConfirmar: () -> Unit = {},
    onIrAPedidos: () -> Unit = {},
    onIrAPerfil: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme

    val productos by vm.productos.collectAsState()
    val carrito by vm.carrito.collectAsState()
    val totalItems = carrito.sumOf { it.cantidad }

    var textoBusqueda by rememberSaveable { mutableStateOf("") }
    var categoriaSeleccionada by rememberSaveable { mutableStateOf("Todo") }
    var mostrarComboDialog by rememberSaveable { mutableStateOf(false) }
    val categorias = listOf("Todo", "Desayunos", "Almuerzos", "Bebidas")

    if (mostrarComboDialog) {
        ComboPresupuestoDialog(
            generarCombo = { monto -> vm.generarComboPorPresupuesto(monto) },
            onDismiss = { mostrarComboDialog = false },
            onAgregarCombo = { combo ->
                vm.agregarCombo(combo)
                mostrarComboDialog = false
            }
        )
    }

    val productosFiltrados = remember(productos, categoriaSeleccionada, textoBusqueda) {
        val base = if (categoriaSeleccionada == "Todo") {
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
        if (textoBusqueda.isBlank()) base else base.filter { it.nombre.contains(textoBusqueda, ignoreCase = true) }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Text("🏠", fontSize = 18.sp) },
                    label = { Text("Menú", color = colorScheme.secondary, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorScheme.secondary.copy(alpha = 0.15f)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onIrAPedidos() },
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = colorScheme.onSurfaceVariant) }
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
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
                        color = colorScheme.onBackground
                    )
                    Text(
                        text = "¿Qué se te antoja hoy?",
                        fontSize = 14.sp,
                        color = colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(colorScheme.surfaceVariant)
                        .clickable { onIrAConfirmar() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🛍️", fontSize = 20.sp)
                    if (totalItems > 0) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(colorScheme.secondary)
                                .align(Alignment.TopEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$totalItems",
                                color = colorScheme.onSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mostrarComboDialog = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.secondary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "🎲 Modo Sorpréndeme",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Armá tu combo según tu presupuesto",
                            fontSize = 12.sp,
                            color = colorScheme.onSecondary.copy(alpha = 0.85f)
                        )
                    }
                    Text("›", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = colorScheme.onSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar en el menú...", color = colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surfaceVariant,
                    unfocusedContainerColor = colorScheme.surfaceVariant,
                    focusedBorderColor = colorScheme.secondary,
                    unfocusedBorderColor = colorScheme.outline,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface
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
                        color = if (esSeleccionado) colorScheme.secondary else colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (esSeleccionado) colorScheme.onSecondary else colorScheme.onSurfaceVariant,
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
                color = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))
            }
            }

            if (productosFiltrados.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay productos en esta categoría",
                            color = colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
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
