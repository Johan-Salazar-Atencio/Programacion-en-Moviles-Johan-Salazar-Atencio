package com.salazar.pokemon.data.repository

import com.salazar.pokemon.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// Datos de prueba; se reemplaza por la implementación con Retrofit y Room
class ItemRepositoryFalso : ItemRepository {

    private val datos = MutableStateFlow(
        listOf(
            Item(4, "charmander", "#4", null),
            Item(5, "charmeleon", "#5", null),
            Item(6, "charizard", "#6", null),
            Item(37, "vulpix", "#37", null),
            Item(38, "ninetales", "#38", null),
            Item(58, "growlithe", "#58", null),
            Item(59, "arcanine", "#59", null),
            Item(77, "ponyta", "#77", null),
            Item(78, "rapidash", "#78", null),
            Item(126, "magmar", "#126", null),
            Item(136, "flareon", "#136", null),
            Item(146, "moltres", "#146", null)
        )
    )

    override val items: Flow<List<Item>> = datos
    override val favoritos: Flow<List<Item>> = datos.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> = Result.success(datos.value.size)

    override suspend fun obtenerDetalle(id: Int): Result<Item> =
        datos.value.firstOrNull { it.id == id }
            ?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException())

    override suspend fun alternarFavorito(id: Int) {
        datos.value = datos.value.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
    }

    override suspend fun cantidadGuardada(): Int = datos.value.size

    override suspend fun cantidadFavoritos(): Int = datos.value.count { it.esFavorito }

    override suspend fun limpiarCache() {
        datos.value = emptyList()
    }
}
