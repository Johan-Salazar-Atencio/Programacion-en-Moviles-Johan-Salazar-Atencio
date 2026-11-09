package com.salazar.pokemon.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.notification.Notificador
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.mensajeDeError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AdminResumen(val guardados: Int, val favoritos: Int)

class AdminViewModel(
    private val repo: ItemRepository,
    private val notificador: Notificador
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<AdminResumen>>(UiState.Cargando)
    val estado: StateFlow<UiState<AdminResumen>> = _estado.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init {
        viewModelScope.launch { actualizarTotales() }
    }

    fun sincronizar() {
        viewModelScope.launch {
            repo.refrescar()
                .onSuccess {
                    _mensaje.value = "Se guardaron $it elementos"
                    notificador.mostrar("Datos actualizados", "Se guardaron $it elementos")
                }
                .onFailure { _mensaje.value = mensajeDeError(it) }
            actualizarTotales()
        }
    }

    fun limpiarCache() {
        viewModelScope.launch {
            repo.limpiarCache()
            _mensaje.value = "Caché limpiada"
            actualizarTotales()
        }
    }

    private suspend fun actualizarTotales() {
        _estado.value = UiState.Exito(AdminResumen(repo.cantidadGuardada(), repo.cantidadFavoritos()))
    }
}
