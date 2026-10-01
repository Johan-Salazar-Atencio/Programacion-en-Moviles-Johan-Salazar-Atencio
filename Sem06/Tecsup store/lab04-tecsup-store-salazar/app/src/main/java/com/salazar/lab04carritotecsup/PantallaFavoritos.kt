package com.salazar.lab04carritotecsup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PantallaFavoritos(
    favoritos: List<Producto>,
    onEliminarFavorito: (Producto) -> Unit = {},
    onFavoritoToggle: (Producto) -> Unit = {}
) {
    if (favoritos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes productos en favoritos",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoritos) { producto ->
                TarjetaProducto(
                    producto = producto,
                    onEliminar = { onEliminarFavorito(producto) },
                    onFavoritoToggle = onFavoritoToggle
                )
            }
        }
    }
}