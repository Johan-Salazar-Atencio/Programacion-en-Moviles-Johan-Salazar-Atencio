package com.tecsup.mibodega.ui.cliente.screens.detalle

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.SelectorCantidad
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.RojoPrecio
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 4: Detalle del producto con sistema de favoritos reactivo.
 */
@Composable
fun DetalleProductoScreen(
    producto: Producto,
    esFavorito: Boolean = false,
    onToggleFavorito: () -> Unit = {},
    onVolver: () -> Unit,
    onAgregarAlCarrito: (Producto, Int) -> Unit
) {
    var cantidad by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        EncabezadoDetalle(
            onVolver = onVolver,
            esFavorito = esFavorito,
            onFavoritoClick = onToggleFavorito
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.3f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = producto.icono,
                    contentDescription = producto.nombre,
                    tint = VerdeBodega,
                    modifier = Modifier.size(96.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (producto.presentacion.isNotBlank()) {
                Text(
                    text = "Presentación: ${producto.presentacion}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "S/ %.2f".format(producto.precio),
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp),
                fontWeight = FontWeight.Bold,
                color = RojoPrecio
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            SelectorCantidad(
                cantidad = cantidad,
                onIncrementar = { cantidad++ },
                onDecrementar = { if (cantidad > 1) cantidad-- }
            )

            Spacer(Modifier.weight(1f))

            BotonPrimario(
                texto = "Agregar al carrito",
                icono = Icons.Default.ShoppingCart,
                onClick = { onAgregarAlCarrito(producto, cantidad) }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun EncabezadoDetalle(
    onVolver: () -> Unit,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit
) {
    val favoritoTint by animateColorAsState(
        targetValue = if (esFavorito) RojoPrecio else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "FavoritoTint"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        IconButton(onClick = onFavoritoClick) {
            Icon(
                imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorito",
                tint = favoritoTint
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetalleProductoPreview() {
    BodegaTheme {
        DetalleProductoScreen(
            producto = listaProductosFake.first { it.nombre == "Coca-Cola Original" },
            esFavorito = true,
            onToggleFavorito = {},
            onVolver = {},
            onAgregarAlCarrito = { _, _ -> }
        )
    }
}
