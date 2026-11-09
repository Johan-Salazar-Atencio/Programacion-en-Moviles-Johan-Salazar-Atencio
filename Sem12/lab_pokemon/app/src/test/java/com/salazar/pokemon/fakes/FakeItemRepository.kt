package com.salazar.pokemon.fakes

import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeItemRepository(
    inicial: List<Item> = emptyList(),
    var resultadoRefresco: Result<Int> = Result.success(inicial.size)
) : ItemRepository {

    private val datos = MutableStateFlow(inicial)

    override val items: Flow<List<Item>> = datos
    override val favoritos: Flow<List<Item>> = datos.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> = resultadoRefresco

    override suspend fun obtenerDetalle(id: Int): Result<Item> =
        datos.value.firstOrNull { it.id == id }
            ?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException())

    override suspend fun alternarFavorito(id: Int) {
        datos.value = datos.value.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
    }

    override suspend fun cantidadGuardada() = datos.value.size

    override suspend fun cantidadFavoritos() = datos.value.count { it.esFavorito }

    override suspend fun limpiarCache() {
        datos.value = emptyList()
    }
}
