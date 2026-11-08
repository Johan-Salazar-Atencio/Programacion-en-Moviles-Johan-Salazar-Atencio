package com.salazar.pokemon.data.remote

import com.salazar.pokemon.data.remote.dto.DetalleDto
import com.salazar.pokemon.data.remote.dto.TipoRespuestaDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("type/fire")
    suspend fun listar(): TipoRespuestaDto

    @GET("pokemon/{id}")
    suspend fun detalle(@Path("id") id: Int): DetalleDto
}
