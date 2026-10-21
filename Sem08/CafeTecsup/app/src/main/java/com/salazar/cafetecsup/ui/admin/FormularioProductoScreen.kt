package com.salazar.cafetecsup.ui.admin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil.compose.AsyncImage
import com.salazar.cafetecsup.ui.components.obtenerImagenProducto
import com.salazar.cafetecsup.viewmodel.MenuAdminViewModel

@Composable
fun FormularioProductoScreen(
    productoId: Int,
    vm: MenuAdminViewModel,
    onVolver: () -> Unit
) {
    val productos by vm.productos.collectAsState()
    val productoExistente = remember(productos, productoId) {
        productos.find { it.id == productoId }
    }

    var nombre by remember(productoExistente) { mutableStateOf(productoExistente?.nombre ?: "") }
    var categoria by remember(productoExistente) { mutableStateOf(productoExistente?.categoria ?: "Desayunos") }
    var precioTexto by remember(productoExistente) { mutableStateOf(productoExistente?.precio?.toString() ?: "") }
    var descripcion by remember(productoExistente) { mutableStateOf(productoExistente?.descripcion ?: "") }
    var disponible by remember(productoExistente) { mutableStateOf(productoExistente?.disponible ?: true) }
    var iconoUri by remember(productoExistente) { mutableStateOf(productoExistente?.icono ?: "cafe") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            iconoUri = uri.toString()
        }
    }

    var errorMensaje by remember { mutableStateOf("") }
    val esEdicion = productoId > 0
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Cabecera
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onVolver() }.padding(end = 16.dp)
            )
            Text(
                text = if (esEdicion) "Editar producto" else "Nuevo producto",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bloque "Imagen del producto" (Galería)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { galleryLauncher.launch("image/*") },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (iconoUri.startsWith("content://") || iconoUri.startsWith("file://")) {
                        AsyncImage(
                            model = iconoUri,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        val imgRes = obtenerImagenProducto(nombre.ifBlank { "café" }, iconoUri)
                        if (imgRes is Int) {
                            Image(
                                painter = painterResource(id = imgRes),
                                contentDescription = "Imagen del producto",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Imagen del producto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFFB45309)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Toca para elegir de la galería",
                        fontSize = 12.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMensaje.isNotBlank()) {
            Surface(
                color = Color(0xFFFEE2E2),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = errorMensaje,
                    color = Color(0xFFDC2626),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Campo Nombre
        Text("Nombre del producto", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; errorMensaje = "" },
            placeholder = { Text("Empanada de carne") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Categoría
        Text("Categoría", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it; errorMensaje = "" },
            placeholder = { Text("Desayunos, Bebidas, Almuerzos") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Precio
        Text("Precio (S/)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = precioTexto,
            onValueChange = { precioTexto = it; errorMensaje = "" },
            placeholder = { Text("4.50") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Descripción
        Text("Descripción", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            placeholder = { Text("Descripción del producto") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Switch Disponible
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Disponible en el menú", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Los estudiantes podrán pedirlo", fontSize = 12.sp, color = Color(0xFF6B7280))
            }
            Switch(
                checked = disponible,
                onCheckedChange = { disponible = it },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF4F46E5))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Guardar
        Button(
            onClick = {
                val precioParsed = precioTexto.toDoubleOrNull()
                if (nombre.isBlank()) {
                    errorMensaje = "El nombre del producto es obligatorio"
                } else if (precioParsed == null || precioParsed <= 0.0) {
                    errorMensaje = "El precio debe ser un número mayor a 0"
                } else {
                    vm.guardar(
                        id = if (esEdicion) productoId else 0,
                        nombre = nombre,
                        categoria = categoria,
                        precio = precioParsed,
                        descripcion = descripcion,
                        disponible = disponible,
                        icono = iconoUri
                    )
                    onVolver()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5))
        ) {
            Text("Guardar producto", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4F46E5))
        ) {
            Text("Cancelar", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF4F46E5))
        }
    }
}
