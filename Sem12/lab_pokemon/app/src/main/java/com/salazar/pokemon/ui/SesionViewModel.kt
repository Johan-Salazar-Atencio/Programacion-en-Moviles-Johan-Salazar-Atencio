package com.salazar.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.auth.AuthRepository
import com.salazar.pokemon.domain.model.Sesion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SesionViewModel(private val auth: AuthRepository) : ViewModel() {

    // Exito(null) significa que no hay sesión iniciada
    private val _sesion = MutableStateFlow<UiState<Sesion?>>(UiState.Cargando)
    val sesion: StateFlow<UiState<Sesion?>> = _sesion.asStateFlow()

    init {
        recargar()
    }

    fun recargar() {
        viewModelScope.launch { _sesion.value = UiState.Exito(auth.sesionActual()) }
    }

    fun cerrarSesion() {
        auth.cerrarSesion()
        _sesion.value = UiState.Exito(null)
    }
}
