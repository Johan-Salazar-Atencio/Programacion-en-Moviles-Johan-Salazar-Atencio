package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.entity.Pedido
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import com.salazar.cafetecsup.data.repository.CafeRepository
import com.salazar.cafetecsup.model.EstadoPedido
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class PedidosAdminViewModel(
    private val repository: CafeRepository
) : ViewModel() {

    val estadoSeleccionado = MutableStateFlow(EstadoPedido.RECIBIDO)

    val pedidos: StateFlow<List<PedidoConDetalle>> = estadoSeleccionado
        .flatMapLatest { estado ->
            repository.observarPedidosPorEstado(estado)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun seleccionarEstado(estado: EstadoPedido) {
        estadoSeleccionado.value = estado
    }

    fun obtenerPedido(id: Int): StateFlow<PedidoConDetalle?> {
        return repository.observarPedido(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    fun cambiarEstadoDirecto(pedidoId: Int, nuevoEstado: EstadoPedido) {
        viewModelScope.launch {
            repository.cambiarEstado(pedidoId, nuevoEstado)
        }
    }

    fun avanzar(pedido: Pedido) {
        val estadoActual = try {
            EstadoPedido.valueOf(pedido.estado)
        } catch (e: Exception) {
            EstadoPedido.RECIBIDO
        }

        val siguienteEstado = when (estadoActual) {
            EstadoPedido.RECIBIDO -> EstadoPedido.PREPARANDO
            EstadoPedido.PREPARANDO -> EstadoPedido.LISTO
            EstadoPedido.LISTO -> EstadoPedido.ENTREGADO
            else -> null
        }

        siguienteEstado?.let { nuevo ->
            viewModelScope.launch {
                repository.cambiarEstado(pedido.id, nuevo)
            }
        }
    }

    fun cancelar(pedido: Pedido) {
        viewModelScope.launch {
            repository.cambiarEstado(pedido.id, EstadoPedido.CANCELADO)
        }
    }
}