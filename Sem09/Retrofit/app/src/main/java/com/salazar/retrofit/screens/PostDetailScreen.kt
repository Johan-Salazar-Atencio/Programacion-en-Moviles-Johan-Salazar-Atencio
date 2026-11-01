package com.salazar.retrofit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.retrofit.viewmodel.PostViewModel

@Composable
fun PostDetailScreen(
    id: Int,
    viewModel: PostViewModel
) {
    val post by viewModel.selectedPost.collectAsState()
    val esFavorito by viewModel.isFavorite.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadPostById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        post?.let { p ->
            OutlinedTextField(
                value = p.id.toString(), onValueChange = {},
                label = { Text("ID") }, readOnly = true
            )
            OutlinedTextField(
                value = p.userId.toString(), onValueChange = {},
                label = { Text("User ID") }, readOnly = true
            )
            OutlinedTextField(
                value = p.title, onValueChange = {},
                label = { Text("Titulo") }, readOnly = true
            )
            OutlinedTextField(
                value = p.body, onValueChange = {},
                label = { Text("Contenido") }, readOnly = true
            )
            Button(
                onClick = { viewModel.toggleFavorite(p) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(if (esFavorito) "Quitar de favoritos" else "Guardar en favoritos")
            }
        } ?: Text("Cargando publicacion...")
    }
}
