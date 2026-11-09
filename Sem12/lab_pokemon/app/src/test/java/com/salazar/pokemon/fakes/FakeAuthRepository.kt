package com.salazar.pokemon.fakes

import com.salazar.pokemon.data.auth.AuthRepository
import com.salazar.pokemon.domain.model.Sesion

class FakeAuthRepository(
    var resultado: Result<Sesion>
) : AuthRepository {

    var intentos = 0

    override suspend fun iniciarSesion(correo: String, clave: String): Result<Sesion> {
        intentos++
        return resultado
    }

    override suspend fun sesionActual(): Sesion? = resultado.getOrNull()

    override fun cerrarSesion() = Unit
}
