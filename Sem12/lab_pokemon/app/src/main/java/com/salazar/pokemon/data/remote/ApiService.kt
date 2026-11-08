package com.salazar.pokemon.data.remote

import com.salazar.pokemon.data.remote.dto.TipoRespuestaDto
import retrofit2.http.GET

interface ApiService {
    @GET("type/fire")
    suspend fun listar(): TipoRespuestaDto
}
