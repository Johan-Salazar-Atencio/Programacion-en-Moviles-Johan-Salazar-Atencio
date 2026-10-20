package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.repository.CafeRepository
import com.salazar.cafetecsup.model.ItemCarrito
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

    fun vaciar() {
        _carrito.value = emptyList()
    }

    fun total(): Double {
        return _carrito.value.sumOf { it.producto.precio * it.cantidad }
    }
}