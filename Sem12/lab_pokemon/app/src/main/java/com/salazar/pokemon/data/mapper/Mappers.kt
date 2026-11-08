package com.salazar.pokemon.data.mapper

import com.salazar.pokemon.data.remote.dto.ItemDto
import com.salazar.pokemon.domain.model.Item

private const val IMAGEN_BASE =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

fun ItemDto.aModelo(esFavorito: Boolean = false): Item {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return Item(
        id = id,
        titulo = name,
        subtitulo = "#$id",
        imagenUrl = "$IMAGEN_BASE$id.png",
        esFavorito = esFavorito
    )
}
