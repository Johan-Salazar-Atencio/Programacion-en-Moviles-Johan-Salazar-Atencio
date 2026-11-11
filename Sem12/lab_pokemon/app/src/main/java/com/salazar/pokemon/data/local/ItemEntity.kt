package com.salazar.pokemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val subtitulo: String,
    val imagenUrl: String?
)

// un favorito pertenece a una cuenta: la misma tabla de Pokémon se comparte entre usuarios
@Entity(tableName = "favoritos", primaryKeys = ["uid", "itemId"])
data class FavoritoEntity(
    val uid: String,
    val itemId: Int
)

data class ItemConFavorito(
    val id: Int,
    val titulo: String,
    val subtitulo: String,
    val imagenUrl: String?,
    val esFavorito: Boolean
)
