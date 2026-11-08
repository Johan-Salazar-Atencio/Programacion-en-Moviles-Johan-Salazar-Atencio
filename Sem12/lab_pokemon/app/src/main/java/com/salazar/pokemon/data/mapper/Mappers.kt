package com.salazar.pokemon.data.mapper

import com.salazar.pokemon.data.remote.dto.DetalleDto
import com.salazar.pokemon.data.remote.dto.ItemDto
import com.salazar.pokemon.domain.model.Item

private const val IMAGEN_BASE =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

private fun imagenDe(id: Int) = "$IMAGEN_BASE$id.png"

fun ItemDto.aModelo(esFavorito: Boolean = false): Item {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return Item(
        id = id,
        titulo = name,
        subtitulo = "#$id",
        imagenUrl = imagenDe(id),
        esFavorito = esFavorito
    )
}

fun DetalleDto.aModelo(esFavorito: Boolean = false) = Item(
    id = id,
    titulo = name,
    subtitulo = "#$id",
    imagenUrl = imagenDe(id),
    esFavorito = esFavorito,
    tipos = types.map { it.type.name },
    alturaDm = height,
    pesoHg = weight
)
