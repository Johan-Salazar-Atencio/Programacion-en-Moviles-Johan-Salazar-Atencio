package com.salazar.pokemon.data.repository

import com.salazar.pokemon.data.mapper.aModelo
import com.salazar.pokemon.data.remote.ApiService
import com.salazar.pokemon.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ItemRepositoryImpl(private val api: ApiService) : ItemRepository {

    private val datos = MutableStateFlow<List<Item>>(emptyList())

    override val items: Flow<List<Item>> = datos
    override val favoritos: Flow<List<Item>> = datos.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> = try {
        val favoritos = datos.value.filter { it.esFavorito }.map { it.id }.toSet()
        val nuevos = api.listar().pokemon
            .map { it.pokemon.aModelo() }
            .map { it.copy(esFavorito = it.id in favoritos) }
        datos.value = nuevos
        Result.success(nuevos.size)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    }

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
