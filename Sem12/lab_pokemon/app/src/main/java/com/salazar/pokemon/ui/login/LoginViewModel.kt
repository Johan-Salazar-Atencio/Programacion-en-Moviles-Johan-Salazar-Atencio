package com.salazar.pokemon.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.auth.AuthRepository
import com.salazar.pokemon.data.auth.CredencialesInvalidasException
import com.salazar.pokemon.data.auth.DemasiadosIntentosException
import com.salazar.pokemon.domain.model.Sesion
import com.salazar.pokemon.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(private val auth: AuthRepository) : ViewModel() {

    // null mientras no se ha intentado ingresar
    private val _estado = MutableStateFlow<UiState<Sesion>?>(null)
    val estado: StateFlow<UiState<Sesion>?> = _estado.asStateFlow()

    fun ingresar(correo: String, clave: String) {
        if (correo.isBlank() || clave.isBlank()) {
            _estado.value = UiState.Error("Ingresa el correo y la contraseña")
            return
        }
        viewModelScope.launch {
            _estado.value = UiState.Cargando
            auth.iniciarSesion(correo.trim(), clave)
                .onSuccess { _estado.value = UiState.Exito(it) }
                .onFailure { _estado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    private fun mensajeDe(e: Throwable) = when (e) {
        is IOException -> "No hay conexión a internet"
        is CredencialesInvalidasException -> "Correo o contraseña incorrectos"
        is DemasiadosIntentosException -> "Demasiados intentos, espera unos minutos"
        else -> "No se pudo iniciar sesión, intenta de nuevo"
    }
}
