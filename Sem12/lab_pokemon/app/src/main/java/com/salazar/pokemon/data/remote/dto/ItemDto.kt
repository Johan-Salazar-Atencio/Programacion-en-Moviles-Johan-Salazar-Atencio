package com.salazar.pokemon.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TipoRespuestaDto(val pokemon: List<EntradaPokemonDto>)

@Serializable
data class EntradaPokemonDto(val pokemon: ItemDto)

@Serializable
data class ItemDto(val name: String, val url: String)
