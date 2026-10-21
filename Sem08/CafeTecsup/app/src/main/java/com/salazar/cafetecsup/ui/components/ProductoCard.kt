package com.salazar.cafetecsup.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.salazar.cafetecsup.R
import com.salazar.cafetecsup.data.local.entity.Producto

@DrawableRes
fun obtenerImagenProducto(nombre: String, icono: String = ""): Int {
    val nombreLower = nombre.lowercase()
    return when {
        nombreLower.contains("americano") -> R.drawable.cafe_americano
        nombreLower.contains("chicha") -> R.drawable.chicha_morada
        nombreLower.contains("empanada") -> R.drawable.empanada
        nombreLower.contains("jugo") || nombreLower.contains("naranja") -> R.drawable.jugo_naranja
        nombreLower.contains("menú") || nombreLower.contains("menu") -> R.drawable.menu_dia
        nombreLower.contains("sándwich") || nombreLower.contains("sandwich") || nombreLower.contains("pollo") -> R.drawable.sandwich_pollo
        else -> R.drawable.empanada
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable(enabled = producto.disponible) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (producto.disponible) Color(0xFFFFF8F1) else Color(0xFFF3F4F6)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (producto.disponible) 2.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFEF3C7)),
                contentAlignment = Alignment.Center
            ) {
                if (producto.icono.startsWith("content://") || producto.icono.startsWith("file://")) {
                    AsyncImage(
                        model = producto.icono,
                        contentDescription = producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = obtenerImagenProducto(producto.nombre, producto.icono)),
                        contentDescription = producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                if (!producto.disponible) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.45f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            color = Color(0xFFEF4444),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Agotado",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (producto.disponible) Color(0xFF111827) else Color(0xFF9CA3AF),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "S/ ${String.format("%.2f", producto.precio)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (producto.disponible) Color(0xFFEA580C) else Color(0xFF9CA3AF)
                )

                if (producto.disponible) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFEA580C)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                } else {
                    Text(
                        text = "No disponible",
                        fontSize = 11.sp,
                        color = Color(0xFFEF4444),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
