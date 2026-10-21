package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.repository.CafeRepository
import com.salazar.cafetecsup.model.ComboSugerido
import com.salazar.cafetecsup.model.ItemCarrito
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MenuViewModel(private val repository: CafeRepository) : ViewModel() {
    val productos: StateFlow<List<Producto>> = repository.observarProductos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito

    fun agregar(producto: Producto, cantidad: Int, nota: String) {
        val listaActual = _carrito.value.toMutableList()

        val index = listaActual.indexOfFirst { it.producto.id == producto.id }

        if (index != -1) {
            val itemActual = listaActual[index]
            listaActual[index] = itemActual.copy(
                cantidad = itemActual.cantidad + cantidad,
                nota = if (nota.isNotBlank()) nota else itemActual.nota
            )
        } else {
            listaActual.add(ItemCarrito(producto = producto, cantidad = cantidad, nota = nota))
        }

        _carrito.value = listaActual
    }

    fun quitar(productoId: Int) {
        _carrito.value = _carrito.value.filterNot { it.producto.id == productoId }
    }

    fun agregarCombo(combo: ComboSugerido) {
        combo.items.forEach { producto ->
            agregar(producto = producto, cantidad = 1, nota = "")
        }
    }

    fun generarComboPorPresupuesto(monto: Double): ComboSugerido? {
        val disponibles = productos.value.filter { it.disponible }
        if (disponibles.size < 2 || monto <= 0.0) return null

        val candidatos = mutableListOf<Pair<List<Producto>, Double>>()

        for (i in disponibles.indices) {
            for (j in i + 1 until disponibles.size) {
                val a = disponibles[i]
                val b = disponibles[j]
                val total = a.precio + b.precio
                if (total <= monto) candidatos.add(listOf(a, b) to total)
            }
        }

        for (i in disponibles.indices) {
            for (j in i + 1 until disponibles.size) {
                for (k in j + 1 until disponibles.size) {
                    val a = disponibles[i]
                    val b = disponibles[j]
                    val c = disponibles[k]
                    val total = a.precio + b.precio + c.precio
                    if (total <= monto) candidatos.add(listOf(a, b, c) to total)
                }
            }
        }

        if (candidatos.isEmpty()) return null

        fun esBebida(categoria: String): Boolean =
            categoria.contains("bebida", ignoreCase = true) ||
                    categoria.contains("jugo", ignoreCase = true) ||
                    categoria.contains("café", ignoreCase = true) ||
                    categoria.contains("cafe", ignoreCase = true)

        fun puntaje(items: List<Producto>, total: Double): Double {
            var score = total
            if (items.map { it.categoria }.distinct().size >= 2) score += 0.6
            val tieneBebida = items.any { esBebida(it.categoria) }
            val tieneComida = items.any { !esBebida(it.categoria) }
            if (tieneBebida && tieneComida) score += 0.4
            return score
        }

        val maxScore = candidatos.maxOf { puntaje(it.first, it.second) }
        val empatados = candidatos.filter { abs(puntaje(it.first, it.second) - maxScore) < 0.001 }
        val elegido = empatados.random()

        return ComboSugerido(items = elegido.first, total = elegido.second)
    }

    fun vaciar() {
        _carrito.value = emptyList()
    }

    fun total(): Double {
        return _carrito.value.sumOf { it.producto.precio * it.cantidad }
    }
}