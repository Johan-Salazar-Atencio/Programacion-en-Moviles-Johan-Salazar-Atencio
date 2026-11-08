package com.salazar.pokemon.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class AdminResumen(val guardados: Int, val favoritos: Int)

class AdminViewModel(private val repo: ItemRepository) : ViewModel() {

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
                .onSuccess { _mensaje.value = "Se guardaron $it elementos" }
                .onFailure { _mensaje.value = mensajeDe(it) }
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

    private fun mensajeDe(e: Throwable) = when (e) {
        is IOException -> "No hay conexión a internet"
        is HttpException -> "El servidor respondió con error ${e.code()}"
        else -> "Ocurrió un error inesperado"
    }
}
