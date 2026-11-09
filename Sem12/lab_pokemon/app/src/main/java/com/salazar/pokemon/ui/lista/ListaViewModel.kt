package com.salazar.pokemon.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.notification.Notificador
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.mensajeDeError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ListaViewModel(
    private val repo: ItemRepository,
    private val notificador: Notificador
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Item>>> = _estado.asStateFlow()

    private val _aviso = MutableStateFlow<String?>(null)
    val aviso: StateFlow<String?> = _aviso.asStateFlow()

    private val _consulta = MutableStateFlow("")
    val consulta: StateFlow<String> = _consulta.asStateFlow()

    // evita mostrar la lista vacía antes de que termine la primera descarga
    private var refrescoTerminado = false

    init {
        viewModelScope.launch {
            combine(repo.items, _consulta) { lista, texto -> filtrar(lista, texto) }
                .collect { lista ->
                    if (lista.isNotEmpty() || refrescoTerminado) _estado.value = UiState.Exito(lista)
                }
        }
        refrescar()
    }

    fun buscar(texto: String) {
        _consulta.value = texto
    }

    private fun filtrar(lista: List<Item>, texto: String): List<Item> =
        if (texto.isBlank()) lista else lista.filter { it.titulo.contains(texto.trim(), ignoreCase = true) }

    fun refrescar() {
        viewModelScope.launch {
            if (_estado.value is UiState.Error) _estado.value = UiState.Cargando
            val resultado = repo.refrescar()
            refrescoTerminado = true
            resultado
                .onSuccess { n ->
                    if (_estado.value is UiState.Cargando) _estado.value = UiState.Exito(emptyList())
                    _aviso.value = null
                    notificador.mostrar("Datos actualizados", "Se guardaron $n elementos")
                }
                .onFailure { e ->
                    if (repo.cantidadGuardada() == 0) {
                        _estado.value = UiState.Error(mensajeDeError(e))
                    } else {
                        _aviso.value = "Sin conexión: mostrando datos guardados"
                    }
                }
        }
    }

    fun alternarFavorito(id: Int) {
        viewModelScope.launch { repo.alternarFavorito(id) }
    }
}
