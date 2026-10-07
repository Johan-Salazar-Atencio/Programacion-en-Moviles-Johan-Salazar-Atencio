package com.tecsup.mibodega.ui.cliente.modelo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Pedido(
    val id: String,
    val items: List<ItemCarrito>,
    val total: Double,
    val direccion: String,
    val metodoPago: String,
    val metodoEntrega: String, // "Delivery" o "Recojo en tienda"
    val fecha: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
    val estado: String = "En camino"
)
