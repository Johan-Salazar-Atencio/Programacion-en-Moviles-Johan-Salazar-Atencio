package com.salazar.pokemon.ui.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetalleViewModel(
    private val repo: ItemRepository,
    private val id: Int
) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<Item>>(UiState.Cargando)
    val estado: StateFlow<UiState<Item>> = _estado.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            repo.obtenerDetalle(id)
                .onSuccess { _estado.value = UiState.Exito(it) }
                .onFailure { _estado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun alternarFavorito() {
        val actual = (_estado.value as? UiState.Exito)?.datos ?: return
        viewModelScope.launch {
            repo.alternarFavorito(id)
            _estado.value = UiState.Exito(actual.copy(esFavorito = !actual.esFavorito))
        }
    }

    private fun mensajeDe(e: Throwable) = when (e) {
        is IOException -> "No hay conexión a internet"
        is HttpException -> "El servidor respondió con error ${e.code()}"
        else -> "Ocurrió un error inesperado"
    }
}
