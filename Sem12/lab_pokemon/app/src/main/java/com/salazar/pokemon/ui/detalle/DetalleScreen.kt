package com.salazar.pokemon.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.components.Cargando
import com.salazar.pokemon.ui.components.ErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(viewModel: DetalleViewModel, onVolver: () -> Unit) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    (estado as? UiState.Exito)?.datos?.let { item ->
                        IconButton(onClick = viewModel::alternarFavorito) {
                            if (item.esFavorito) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Quitar de favoritos",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            } else {
                                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Marcar como favorito")
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when (val e = estado) {
            is UiState.Cargando -> Cargando(modifier)
            is UiState.Error -> ErrorView(e.mensaje, onReintentar = viewModel::cargar, modifier = modifier)
            is UiState.Exito -> Contenido(e.datos, modifier)
        }
    }
}

@Composable
private fun Contenido(item: Item, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = item.imagenUrl,
            contentDescription = item.titulo,
            modifier = Modifier.fillMaxWidth().height(260.dp)
        )
        Text(item.titulo.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.headlineMedium)
        Text(item.subtitulo, style = MaterialTheme.typography.titleMedium)
        Text("Tipos: ${item.tipos.joinToString(", ")}")
        item.alturaDm?.let { Text("Altura: ${it / 10.0} m") }
        item.pesoHg?.let { Text("Peso: ${it / 10.0} kg") }

        if (item.estadisticas.isNotEmpty()) {
            Text("Estadísticas base", style = MaterialTheme.typography.titleMedium)
            item.estadisticas.forEach { Estadistica(it) }
        }
    }
}

@Composable
private fun Estadistica(estadistica: com.salazar.pokemon.domain.model.Estadistica) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(estadistica.nombre)
            Text(estadistica.valor.toString())
        }
        LinearProgressIndicator(
            progress = { (estadistica.valor / VALOR_MAXIMO).coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private const val VALOR_MAXIMO = 255f
