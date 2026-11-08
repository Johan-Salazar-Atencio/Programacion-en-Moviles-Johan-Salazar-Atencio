package com.salazar.pokemon.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DetalleDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TipoSlotDto>
)

@Serializable
data class TipoSlotDto(val type: NombreDto)

@Serializable
data class NombreDto(val name: String)
