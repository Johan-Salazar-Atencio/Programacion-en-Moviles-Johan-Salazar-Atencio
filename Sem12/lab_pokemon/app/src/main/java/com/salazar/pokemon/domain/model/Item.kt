package com.salazar.pokemon.domain.model

data class Item(
    val id: Int,
    val titulo: String,
    val subtitulo: String,
    val imagenUrl: String?,
    val esFavorito: Boolean = false,
    val tipos: List<String> = emptyList(),
    val alturaDm: Int? = null,
    val pesoHg: Int? = null
)
