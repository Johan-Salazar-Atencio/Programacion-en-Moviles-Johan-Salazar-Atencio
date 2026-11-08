package com.salazar.pokemon.data.repository

import com.salazar.pokemon.data.local.ItemDao
import com.salazar.pokemon.data.mapper.aEntity
import com.salazar.pokemon.data.mapper.aModelo
import com.salazar.pokemon.data.remote.ApiService
import com.salazar.pokemon.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ItemRepositoryImpl(
    private val api: ApiService,
    private val dao: ItemDao
) : ItemRepository {

    override val items: Flow<List<Item>> = dao.todos().map { lista -> lista.map { it.aModelo() } }
    override val favoritos: Flow<List<Item>> = dao.favoritos().map { lista -> lista.map { it.aModelo() } }

    override suspend fun refrescar(): Result<Int> = try {
        // el upsert sobrescribe esFavorito, por eso se leen antes y se reaplican
        val favoritos = dao.idsFavoritos().toSet()
        val nuevos = api.listar().pokemon
            .map { it.pokemon.aEntity() }
            .map { it.copy(esFavorito = it.id in favoritos) }
        dao.guardarTodos(nuevos)
        Result.success(nuevos.size)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> = try {
        val esFavorito = id in dao.idsFavoritos()
        Result.success(api.detalle(id).aModelo(esFavorito))
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    }

    override suspend fun alternarFavorito(id: Int) = dao.alternarFavorito(id)

    override suspend fun cantidadGuardada() = dao.contar()

    override suspend fun cantidadFavoritos() = dao.contarFavoritos()

    override suspend fun limpiarCache() = dao.borrarTodo()
}
