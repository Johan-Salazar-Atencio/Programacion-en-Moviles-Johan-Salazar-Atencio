package com.salazar.pokemon.ui.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.salazar.pokemon.domain.model.Estadistica
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.components.Cargando
import com.salazar.pokemon.ui.components.ErrorView
import com.salazar.pokemon.ui.components.TipoChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(viewModel: DetalleViewModel, onVolver: () -> Unit) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    (estado as? UiState.Exito)?.datos?.let { item ->
                        FilledTonalIconToggleButton(
                            checked = item.esFavorito,
                            onCheckedChange = { viewModel.alternarFavorito() },
                            colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                                checkedContainerColor = MaterialTheme.colorScheme.primary,
                                checkedContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            if (item.esFavorito) {
                                Icon(Icons.Filled.Favorite, contentDescription = "Quitar de favoritos")
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
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = item.imagenUrl,
                contentDescription = item.titulo,
                modifier = Modifier.size(240.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = item.titulo.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = item.subtitulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item.tipos.forEach { TipoChip(it) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item.alturaDm?.let { Dato("Altura", "${it / 10.0} m", Modifier.weight(1f)) }
            item.pesoHg?.let { Dato("Peso", "${it / 10.0} kg", Modifier.weight(1f)) }
        }

        if (item.estadisticas.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("Estadísticas base", style = MaterialTheme.typography.titleLarge)
                    item.estadisticas.forEach { Barra(it) }
                }
            }
        }
    }
}

@Composable
private fun Dato(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(titulo, style = MaterialTheme.typography.labelMedium)
            Text(valor, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
private fun Barra(estadistica: Estadistica) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(estadistica.nombre, style = MaterialTheme.typography.bodyLarge)
            Text(estadistica.valor.toString(), style = MaterialTheme.typography.titleMedium)
        }
        LinearProgressIndicator(
            progress = { (estadistica.valor / VALOR_MAXIMO).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(CircleShape),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
    }
}

private const val VALOR_MAXIMO = 255f
