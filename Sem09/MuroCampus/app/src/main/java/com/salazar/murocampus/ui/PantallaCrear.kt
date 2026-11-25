package com.salazar.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.murocampus.viewmodel.PostsViewModel
import com.salazar.murocampus.viewmodel.UiState

@Composable
fun PantallaCrear(vm: PostsViewModel, onVolver: () -> Unit) {
    val estado by vm.publicado.collectAsStateWithLifecycle()
    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }

    Scaffold(topBar = { BarraMuro("Nueva publicación", onAtras = onVolver) }) { relleno ->
        Column(
            modifier = Modifier
                .padding(relleno)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { vm.publicar(titulo.trim(), contenido.trim()) },
                enabled = titulo.isNotBlank() && contenido.isNotBlank() && estado !is UiState.Cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (estado is UiState.Cargando) "Publicando…" else "Publicar")
            }
            when (val e = estado) {
                is UiState.Exito -> Text(
                    "Publicado con id ${e.datos.id}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                is UiState.Error -> Text(
                    e.mensaje,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                else -> Unit
            }
        }
    }
}
