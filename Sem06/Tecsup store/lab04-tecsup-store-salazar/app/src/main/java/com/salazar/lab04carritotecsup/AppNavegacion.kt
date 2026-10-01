package com.salazar.lab04carritotecsup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var destinoActual by remember { mutableStateOf(DestinoDrawer.INICIO) }

    // Estado persistente de listas entre cambios de pantalla
    val productos = remember { mutableStateListOf<Producto>() }
    val favoritos = remember { mutableStateListOf<Producto>() }

    val onToggleFavorito: (Producto) -> Unit = { producto ->
        if (favoritos.contains(producto)) {
            favoritos.remove(producto)
        } else {
            favoritos.add(producto)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                destinoSeleccionado = destinoActual,
                onDestinoSeleccionado = { destino ->
                    destinoActual = destino
                    scope.launch { drawerState.close() }
                },
                cantidadFavoritos = favoritos.size
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú de navegación"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (destinoActual) {
                    DestinoDrawer.INICIO -> PantallaCarrito(
                        productos = productos,
                        onAgregarProducto = { producto -> productos.add(producto) },
                        onEliminarProducto = { producto ->
                            productos.remove(producto)
                            favoritos.remove(producto)
                        },
                        onFavoritoToggle = onToggleFavorito
                    )
                    DestinoDrawer.FAVORITOS -> PantallaFavoritos(
                        favoritos = favoritos,
                        onEliminarFavorito = { producto ->
                            favoritos.remove(producto)
                        },
                        onFavoritoToggle = onToggleFavorito
                    )
                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sección: ${destinoActual.titulo}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        }
    }
}