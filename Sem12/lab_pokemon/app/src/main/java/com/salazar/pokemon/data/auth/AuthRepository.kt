package com.salazar.pokemon.data.auth

import com.salazar.pokemon.domain.model.Sesion

class CredencialesInvalidasException : Exception()

class DemasiadosIntentosException : Exception()

interface AuthRepository {
    suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion>

    // null si no hay sesión
    suspend fun sesionActual(): Sesion?

    fun cerrarSesion()
}
