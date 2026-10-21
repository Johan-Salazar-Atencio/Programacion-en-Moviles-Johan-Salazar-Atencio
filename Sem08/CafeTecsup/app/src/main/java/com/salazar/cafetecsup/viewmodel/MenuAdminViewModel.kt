package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.repository.CafeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuAdminViewModel(
    private val repository: CafeRepository
) : ViewModel() {

    val productos: StateFlow<List<Producto>> = repository.observarProductos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun alternarDisponibilidad(producto: Producto) {
        viewModelScope.launch {
            repository.cambiarDisponibilidad(producto.id, !producto.disponible)
        }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch {
            repository.eliminarProducto(producto)
        }
    }

    fun guardar(
        id: Int = 0,
        nombre: String,
        categoria: String,
        precio: Double,
        descripcion: String,
        disponible: Boolean,
        icono: String = "cafe"
    ) {
        viewModelScope.launch {
            val producto = Producto(
                id = id,
                nombre = nombre,
                categoria = categoria,
                precio = precio,
                descripcion = descripcion,
                disponible = disponible,
                icono = icono
            )
            repository.guardarProducto(producto)
        }
    }
}
