package com.salazar.cafetecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.cafetecsup.data.local.entity.Usuario
import com.salazar.cafetecsup.data.prefs.PreferenciasStore
import com.salazar.cafetecsup.data.repository.CafeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repository: CafeRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    val modoOscuro = prefs.modoOscuro.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val perfilActivo = prefs.perfilActivo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    fun login(correo: String, clave: String, rolSeleccionado: String) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            val usuario = repository.login(correo, clave)

            if (usuario == null) {
                _error.value = "Correo o contraseña incorrectos"
            } else if (usuario.rol != rolSeleccionado) {
                _error.value = "El rol seleccionado no coincide con tu cuenta"
            } else {
                _error.value = ""
                _usuarioActual.value = usuario
                prefs.guardarPerfil(usuario.rol)
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            _usuarioActual.value = null
            prefs.guardarPerfil("NINGUNO")
        }
    }

    fun limpiarError() {
        _error.value = ""
    }
}