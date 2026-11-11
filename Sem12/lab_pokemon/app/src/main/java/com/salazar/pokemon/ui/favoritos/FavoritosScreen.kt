package com.salazar.pokemon.ui.favoritos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.components.Cargando
import com.salazar.pokemon.ui.components.ErrorView
import com.salazar.pokemon.ui.components.EstadoVacio
import com.salazar.pokemon.ui.components.ItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    viewModel: FavoritosViewModel,
    onItemClick: (Int) -> Unit
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Favoritos") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when (val e = estado) {
            is UiState.Cargando -> Cargando(modifier)
            is UiState.Error -> ErrorView(e.mensaje, onReintentar = {}, modifier = modifier)
            is UiState.Exito ->
                if (e.datos.isEmpty()) {
                    EstadoVacio(Icons.Outlined.FavoriteBorder, "Aún no tienes favoritos", modifier)
                } else {
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(e.datos, key = { it.id }) { item ->
                            ItemCard(
                                item = item,
                                onClick = { onItemClick(item.id) },
                                onFavorito = { viewModel.alternarFavorito(item.id) }
                            )
                        }
                    }
                }
        }
    }
}
