package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import com.salazar.cafetecsup.data.repository.CafeRepository
import com.salazar.cafetecsup.model.ItemCarrito
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PedidosClienteViewModel(
    private val repository: CafeRepository
) : ViewModel() {

    // Observa pedidos del cliente (clienteId por defecto = 1)
    val todosLosPedidos: StateFlow<List<PedidoConDetalle>> = repository.observarPedidosCliente(1)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun confirmar(
        usuarioId: Int,
        items: List<ItemCarrito>,
        horaRecojo: String,
        pago: String,
        nota: String,
        onExito: (Long) -> Unit
    ) {
        if (items.isEmpty()) return

        viewModelScope.launch {
            val pedidoIdGenerado = repository.crearPedido(
                clienteId = if (usuarioId <= 0) 1 else usuarioId,
                items = items,
                horaRecojo = horaRecojo,
                pago = pago,
                nota = nota
            )
            onExito(pedidoIdGenerado)
        }
    }

    fun obtenerPedido(id: Int): StateFlow<PedidoConDetalle?> {
        return repository.observarPedido(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }
}