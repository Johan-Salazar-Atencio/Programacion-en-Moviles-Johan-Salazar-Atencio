package com.salazar.lab04carritotecsup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PantallaCarrito() {
    // Estados para los inputs
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    // Lista observable: cuando cambie, Compose redibuja la lista [id="1"]
    val productos = remember { mutableStateListOf<Producto>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Formulario
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = precio, onValueChange = { precio = it }, label = { Text("Precio") },
                modifier = Modifier.weight(1f),
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = cantidad, onValueChange = { cantidad = it }, label = { Text("Cantidad") },
                modifier = Modifier.weight(1f),
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Button(
            onClick = {
                val precioNum = precio.toDoubleOrNull() ?: 0.0
                val cantidadNum = cantidad.toIntOrNull() ?: 0
                if (nombre.isNotBlank() && precioNum > 0 && cantidadNum > 0) {
                    productos.add(Producto(1, nombre, precioNum, cantidadNum))
                    nombre = ""
                    precio = ""
                    cantidad = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("AGREGAR") }

        // Lista o Estado Vacío
        if (productos.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Text(text = "Estado de la lista vacía\nAgrega productos...", textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(productos) { p ->
                    TarjetaProducto(producto = p, onEliminar = { productos.remove(p) })
                }
            }
        }

        //Panel de Totales
        Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 8.dp, shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Productos: ${productos.size}")
                    Text("Subtotal: S/ ${productos.sumOf { it.precio * it.cantidad }}")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("IGV (18%)")
                    Text("S/ ${productos.sumOf { it.precio * it.cantidad } * 0.18}")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("TOTAL", style = MaterialTheme.typography.titleLarge)
                    Text("S/ ${productos.sumOf { it.precio * it.cantidad } * 1.18}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}