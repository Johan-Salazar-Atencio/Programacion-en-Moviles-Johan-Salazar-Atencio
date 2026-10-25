package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.model.ComboSugerido
import com.salazar.cafetecsup.ui.components.obtenerImagenProducto

@Composable
fun ComboPresupuestoDialog(
    generarCombo: (Double) -> ComboSugerido?,
    onDismiss: () -> Unit,
    onAgregarCombo: (ComboSugerido) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    var presupuestoTexto by rememberSaveable { mutableStateOf("") }
    var combo by remember { mutableStateOf<ComboSugerido?>(null) }
    var mensaje by rememberSaveable { mutableStateOf("") }

    val montosRapidos = listOf(6.0, 8.0, 10.0, 12.0, 15.0)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = colorScheme.surface,
        title = {
            Column {
                Text(
                    text = "🎲 Modo Sorpréndeme",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorScheme.onSurface
                )
                Text(
                    text = "Armá tu combo según tu presupuesto",
                    fontSize = 13.sp,
                    color = colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                Text(
                    text = "¿Cuánto quieres gastar hoy?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = presupuestoTexto,
                    onValueChange = { valor ->
                        presupuestoTexto = valor.filter { it.isDigit() || it == '.' }
                        mensaje = ""
                        combo = null
                    },
                    placeholder = { Text("Ej. 8.50", color = colorScheme.onSurfaceVariant) },
                    prefix = { Text("S/", color = colorScheme.onSurfaceVariant) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceVariant,
                        unfocusedContainerColor = colorScheme.surfaceVariant,
                        focusedBorderColor = colorScheme.secondary,
                        unfocusedBorderColor = colorScheme.outline,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(montosRapidos) { monto ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    presupuestoTexto = if (monto % 1.0 == 0.0) {
                                        monto.toInt().toString()
                                    } else {
                                        monto.toString()
                                    }
                                    mensaje = ""
                                    combo = null
                                },
                            color = colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "S/ ${if (monto % 1.0 == 0.0) monto.toInt() else monto}",
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                color = colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        val monto = presupuestoTexto.toDoubleOrNull()
                        if (monto == null || monto <= 0.0) {
                            combo = null
                            mensaje = "Ingresa un presupuesto válido"
                        } else {
                            val resultado = generarCombo(monto)
                            if (resultado == null) {
                                combo = null
                                mensaje = "Presupuesto insuficiente para armar un combo"
                            } else {
                                combo = resultado
                                mensaje = ""
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.onSecondary
                    )
                ) {
                    Text("🎲 Generar combo", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                if (mensaje.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = colorScheme.error.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = mensaje,
                            color = colorScheme.error,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                combo?.let { c ->
                    Spacer(modifier = Modifier.height(14.dp))
                    ComboResultado(combo = c, colorScheme = colorScheme)
                }
            }
        },
        confirmButton = {
            combo?.let { c ->
                Button(
                    onClick = {
                        onAgregarCombo(c)
                        onDismiss()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    Text("🛒 Agregar combo al carrito", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar", color = colorScheme.onSurfaceVariant)
            }
        }
    )
}

@Composable
private fun ComboResultado(combo: ComboSugerido, colorScheme: ColorScheme) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Tu combo ideal",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            combo.items.forEach { producto ->
                ComboItemRow(producto = producto, colorScheme = colorScheme)
                Spacer(modifier = Modifier.height(8.dp))
            }

            HorizontalDivider(color = colorScheme.outline)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = colorScheme.outline)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Precio total del combo",
                    fontSize = 13.sp,
                    color = colorScheme.onSurfaceVariant
                )
                Text(
                    text = "S/ ${String.format("%.2f", combo.total)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun ComboItemRow(producto: Producto, colorScheme: ColorScheme) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorScheme.surface),
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
                    painter = painterResource(id = obtenerImagenProducto(producto.nombre)),
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = producto.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorScheme.onSurface
            )
            Text(
                text = producto.categoria,
                fontSize = 11.sp,
                color = colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "S/ ${String.format("%.2f", producto.precio)}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = colorScheme.onSurface
        )
    }
}
