package com.salazar.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.murocampus.viewmodel.PostsViewModel
import com.salazar.murocampus.viewmodel.UiState

@Composable
fun PantallaLista(vm: PostsViewModel, onAbrir: (Int) -> Unit, onCrear: () -> Unit) {
    val estado by vm.listaVisible.collectAsStateWithLifecycle()
    val busqueda by vm.busqueda.collectAsStateWithLifecycle()
    val filtro by vm.usuarioFiltro.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { BarraMuro("MuroCampus") },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrear) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { relleno ->
        Column(Modifier.padding(relleno)) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = vm::buscar,
                label = { Text("Buscar por título") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
            )
            FilaDeFiltros(filtro, vm::filtrar)
            when (val e = estado) {
                UiState.Cargando -> Cargando("Cargando publicaciones…")
                is UiState.Error -> ErrorConReintento(e.mensaje, vm::cargar)
                is UiState.Exito -> if (e.datos.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Sin publicaciones", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(e.datos, key = { it.id }) { post ->
                            TarjetaPost(
                                userId = post.userId,
                                titulo = post.title,
                                cuerpo = post.body,
                                onClick = { onAbrir(post.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaDeFiltros(seleccionado: Int?, onElegir: (Int?) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = seleccionado == null,
                onClick = { onElegir(null) },
                label = { Text("Todos") }
            )
        }
        items((1..10).toList()) { usuario ->
            FilterChip(
                selected = seleccionado == usuario,
                onClick = { onElegir(usuario) },
                label = { Text("Usuario $usuario") }
            )
        }
    }
}
