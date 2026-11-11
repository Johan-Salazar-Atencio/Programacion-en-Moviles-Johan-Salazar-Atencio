package com.salazar.pokemon.data.mapper

import com.salazar.pokemon.data.local.ItemConFavorito
import com.salazar.pokemon.data.local.ItemEntity
import com.salazar.pokemon.data.remote.dto.DetalleDto
import com.salazar.pokemon.data.remote.dto.ItemDto
import com.salazar.pokemon.domain.model.Estadistica
import com.salazar.pokemon.domain.model.Item

private const val IMAGEN_BASE =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

private val NOMBRES_ESTADISTICAS = linkedMapOf(
    "hp" to "HP",
    "attack" to "Ataque",
    "defense" to "Defensa",
    "speed" to "Velocidad"
)

private fun imagenDe(id: Int) = "$IMAGEN_BASE$id.png"

fun ItemDto.aEntity(): ItemEntity {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return ItemEntity(
        id = id,
        titulo = name,
        subtitulo = "#$id",
        imagenUrl = imagenDe(id)
    )
}

fun ItemConFavorito.aModelo() = Item(
    id = id,
    titulo = titulo,
    subtitulo = subtitulo,
    imagenUrl = imagenUrl,
    esFavorito = esFavorito
)

fun DetalleDto.aModelo(esFavorito: Boolean = false) = Item(
    id = id,
    titulo = name,
    subtitulo = "#$id",
    imagenUrl = imagenDe(id),
    esFavorito = esFavorito,
    tipos = types.map { it.type.name },
    alturaDm = height,
    pesoHg = weight,
    estadisticas = stats.mapNotNull { dto ->
        NOMBRES_ESTADISTICAS[dto.stat.name]?.let { Estadistica(it, dto.baseStat) }
    }
)
