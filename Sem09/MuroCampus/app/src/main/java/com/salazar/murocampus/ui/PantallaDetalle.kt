package com.salazar.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.murocampus.viewmodel.PostsViewModel
import com.salazar.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(vm: PostsViewModel, id: Int, onVolver: () -> Unit) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    val borrado by vm.borrado.collectAsStateWithLifecycle()

    LaunchedEffect(borrado) {
        if (borrado is UiState.Exito) {
            vm.limpiarBorrado()
            onVolver()
        }
    }

    Scaffold(topBar = { BarraMuro("Publicación", onAtras = onVolver) }) { relleno ->
        val modificador = Modifier.padding(relleno)
        when (val e = estado) {
            UiState.Cargando -> Cargando("Cargando publicación…", modificador)
            is UiState.Error -> ErrorConReintento(e.mensaje, { vm.abrir(id) }, modificador)
            is UiState.Exito -> LazyColumn(
                modifier = modificador,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "Usuario ${e.datos.post.userId}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            e.datos.post.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(e.datos.post.body, style = MaterialTheme.typography.bodyLarge)
                        OutlinedButton(
                            onClick = { vm.borrar(id) },
                            enabled = borrado !is UiState.Cargando
                        ) {
                            Text(
                                if (borrado is UiState.Cargando) "Eliminando…" else "Eliminar",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        (borrado as? UiState.Error)?.let {
                            Text(it.mensaje, color = MaterialTheme.colorScheme.error)
                        }
                        Text(
                            "Comentarios (${e.datos.comentarios.size})",
                            Modifier.padding(top = 12.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(e.datos.comentarios, key = { it.id }) { comentario ->
                    Card(
                        Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                comentario.email,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(comentario.body, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
