package com.salazar.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.murocampus.viewmodel.PostsViewModel
import com.salazar.murocampus.viewmodel.UiState

@Composable
fun PantallaLista(vm: PostsViewModel, onAbrir: (Int) -> Unit) {
    val estado by vm.lista.collectAsStateWithLifecycle()

    Scaffold(topBar = { BarraMuro("MuroCampus") }) { relleno ->
        val modificador = Modifier.padding(relleno)
        when (val e = estado) {
            UiState.Cargando -> Cargando("Cargando publicaciones…", modificador)
            is UiState.Error -> ErrorConReintento(e.mensaje, vm::cargar, modificador)
            is UiState.Exito -> LazyColumn(
                modifier = modificador,
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
