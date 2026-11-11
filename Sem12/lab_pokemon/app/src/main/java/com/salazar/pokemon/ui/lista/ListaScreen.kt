package com.salazar.pokemon.ui.lista

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.components.Cargando
import com.salazar.pokemon.ui.components.ErrorView
import com.salazar.pokemon.ui.components.EstadoVacio
import com.salazar.pokemon.ui.components.ItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    viewModel: ListaViewModel,
    onItemClick: (Int) -> Unit,
    onFavoritos: () -> Unit,
    esAdmin: Boolean,
    onAdmin: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val aviso by viewModel.aviso.collectAsStateWithLifecycle()
    val consulta by viewModel.consulta.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(aviso) {
        val mensaje = aviso
        if (mensaje == null) {
            snackbarHostState.currentSnackbarData?.dismiss()
        } else {
            snackbarHostState.showSnackbar(
                message = mensaje,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            LargeTopAppBar(
                title = { Text("Pokémon de fuego") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = viewModel::refrescar) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Sincronizar")
                    }
                    IconButton(onClick = onFavoritos) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Ver favoritos")
                    }
                    if (esAdmin) {
                        IconButton(onClick = onAdmin) {
                            Icon(Icons.Filled.Settings, contentDescription = "Panel Admin")
                        }
                    }
                    IconButton(onClick = onCerrarSesion) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when (val e = estado) {
            is UiState.Cargando -> Cargando(modifier)
            is UiState.Error -> ErrorView(e.mensaje, onReintentar = viewModel::refrescar, modifier = modifier)
            is UiState.Exito ->
                if (e.datos.isEmpty() && consulta.isBlank()) {
                    EstadoVacio(Icons.Filled.Refresh, "No hay Pokémon guardados", modifier)
                } else {
                    ListaItems(
                        datos = e.datos,
                        consulta = consulta,
                        onBuscar = viewModel::buscar,
                        onItemClick = onItemClick,
                        onFavorito = viewModel::alternarFavorito,
                        modifier = modifier
                    )
                }
        }
    }
}

@Composable
private fun ListaItems(
    datos: List<Item>,
    consulta: String,
    onBuscar: (String) -> Unit,
    onItemClick: (Int) -> Unit,
    onFavorito: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(key = "busqueda") { CampoBusqueda(consulta, onBuscar) }
        if (datos.isEmpty()) {
            item(key = "vacio") {
                EstadoVacio(
                    Icons.Filled.Search,
                    "Sin resultados para «${consulta.trim()}»",
                    Modifier.fillParentMaxHeight(0.6f)
                )
            }
        } else {
            item(key = "contador") {
                Text(
                    text = if (consulta.isBlank()) "${datos.size} Pokémon" else "${datos.size} resultados",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            items(datos, key = { it.id }) { item ->
                ItemCard(
                    item = item,
                    onClick = { onItemClick(item.id) },
                    onFavorito = { onFavorito(item.id) }
                )
            }
        }
    }
}

@Composable
private fun CampoBusqueda(consulta: String, onCambio: (String) -> Unit) {
    TextField(
        value = consulta,
        onValueChange = onCambio,
        placeholder = { Text("Buscar por nombre") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (consulta.isNotEmpty()) {
                IconButton(onClick = { onCambio("") }) {
                    Icon(Icons.Filled.Close, contentDescription = "Borrar búsqueda")
                }
            }
        },
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
