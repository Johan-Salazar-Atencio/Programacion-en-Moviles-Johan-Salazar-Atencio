package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import com.salazar.cafetecsup.data.repository.CafeRepository
import com.salazar.cafetecsup.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class PanelAdminViewModel(
    private val repository: CafeRepository
) : ViewModel() {

    private val inicioDiaHoy: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

    val nuevosCount: StateFlow<Int> = repository.observarCantidadPorEstado(EstadoPedido.RECIBIDO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val preparandoCount: StateFlow<Int> = repository.observarCantidadPorEstado(EstadoPedido.PREPARANDO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val listosCount: StateFlow<Int> = repository.observarCantidadPorEstado(EstadoPedido.LISTO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val ventasHoy: StateFlow<Double> = repository.observarVentasHoy(inicioDiaHoy)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val pedidosHoy: StateFlow<Int> = repository.observarPedidosHoy(inicioDiaHoy)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val nuevosPedidos: StateFlow<List<PedidoConDetalle>> = repository.observarPedidosPorEstado(EstadoPedido.RECIBIDO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun pasarAPreparando(pedidoId: Int) {
        viewModelScope.launch {
            repository.cambiarEstado(pedidoId, EstadoPedido.PREPARANDO)
        }
    }
}