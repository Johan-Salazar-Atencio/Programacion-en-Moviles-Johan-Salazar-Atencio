package com.salazar.pokemon.data.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// uid de la cuenta con sesión iniciada; null si no hay ninguna
class UsuarioActual {
    private val _uid = MutableStateFlow<String?>(null)
    val uid: StateFlow<String?> = _uid.asStateFlow()

    fun establecer(uid: String?) {
        _uid.value = uid
    }
}
