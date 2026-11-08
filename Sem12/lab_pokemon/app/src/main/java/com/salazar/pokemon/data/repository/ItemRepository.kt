package com.salazar.pokemon.data.repository

import com.salazar.pokemon.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    // sale de Room
    val items: Flow<List<Item>>
    val favoritos: Flow<List<Item>>

    // Retrofit -> Room; devuelve cuántos guardó
    suspend fun refrescar(): Result<Int>

    suspend fun obtenerDetalle(id: Int): Result<Item>
    suspend fun alternarFavorito(id: Int)
    suspend fun cantidadGuardada(): Int
    suspend fun cantidadFavoritos(): Int
    suspend fun limpiarCache()
}
