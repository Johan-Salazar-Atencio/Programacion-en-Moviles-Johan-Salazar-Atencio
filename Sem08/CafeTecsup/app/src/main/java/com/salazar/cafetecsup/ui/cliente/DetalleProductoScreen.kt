package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.ui.components.obtenerImagenProducto
import com.salazar.cafetecsup.viewmodel.MenuViewModel

@Composable
fun DetalleProductoScreen(
    productoId: Int,
    vm: MenuViewModel,
    onVolver: () -> Unit
) {
    val productos by vm.productos.collectAsState()
    val producto = productos.find { it.id == productoId } ?: return

    var cantidad by remember { mutableStateOf(1) }
    var nota by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onVolver() }
                    .padding(end = 16.dp)
            )
            Text(
                text = "Detalle del producto",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFFEF3C7)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = obtenerImagenProducto(producto.nombre)),
                contentDescription = producto.nombre,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = producto.nombre,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
            Text(
                text = "S/ ${String.format("%.2f", producto.precio)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEA580C)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
                color = Color(0xFFD1FAE5),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Disponible",
                    color = Color(0xFF059669),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            Surface(
                color = Color(0xFFF3F4F6),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "⏱ 10 - 15 min",
                    color = Color(0xFF4B5563),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = producto.descripcion.ifBlank { "Preparado al instante con insumos frescos de la cafetería." },
            fontSize = 14.sp,
            color = Color(0xFF6B7280)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cantidad",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF3F4F6))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFDE68A))
                        .clickable { if (cantidad > 1) cantidad-- },
                    contentAlignment = Alignment.Center
                ) {
                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "$cantidad",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEA580C))
                        .clickable { cantidad++ },
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nota para la cocina",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151)
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = nota,
            onValueChange = { nota = it },
            placeholder = { Text("Sin ají, por favor", color = Color(0xFF9CA3AF)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFEA580C),
                unfocusedBorderColor = Color(0xFFE5E7EB)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Total", fontSize = 12.sp, color = Color(0xFF6B7280))
                Text(
                    text = "S/ ${String.format("%.2f", producto.precio * cantidad)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEA580C)
                )
            }

            Button(
                onClick = {
                    vm.agregar(producto, cantidad, nota)
                    onVolver()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C))
            ) {
                Text(
                    text = "Agregar al pedido",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}