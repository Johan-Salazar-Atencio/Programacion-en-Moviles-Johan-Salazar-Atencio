package com.salazar.pokemon.data.repository

import com.salazar.pokemon.data.auth.UsuarioActual
import com.salazar.pokemon.data.local.FavoritoEntity
import com.salazar.pokemon.data.local.ItemDao
import com.salazar.pokemon.data.mapper.aEntity
import com.salazar.pokemon.data.mapper.aModelo
import com.salazar.pokemon.data.remote.ApiService
import com.salazar.pokemon.domain.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ItemRepositoryImpl(
    private val api: ApiService,
    private val dao: ItemDao,
    private val usuario: UsuarioActual
) : ItemRepository {

    // sin sesión no hay favoritos: el uid vacío no coincide con ninguna cuenta
    private val uid get() = usuario.uid.value.orEmpty()

    override val items: Flow<List<Item>> = usuario.uid.flatMapLatest { cuenta ->
        dao.todos(cuenta.orEmpty()).map { lista -> lista.map { it.aModelo() } }
    }

    override val favoritos: Flow<List<Item>> = usuario.uid.flatMapLatest { cuenta ->
        dao.favoritos(cuenta.orEmpty()).map { lista -> lista.map { it.aModelo() } }
    }

    override suspend fun refrescar(): Result<Int> = try {
        val nuevos = api.listar().pokemon.map { it.pokemon.aEntity() }
        dao.guardarTodos(nuevos)
        Result.success(nuevos.size)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: SerializationException) {
        Result.failure(e)
    }

    override suspend fun obtenerDetalle(id: Int): Result<Item> = try {
        Result.success(api.detalle(id).aModelo(dao.esFavorito(uid, id)))
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: SerializationException) {
        Result.failure(e)
    }

    override suspend fun alternarFavorito(id: Int) {
        val cuenta = uid
        if (cuenta.isEmpty()) return
        if (dao.quitarFavorito(cuenta, id) == 0) dao.agregarFavorito(FavoritoEntity(cuenta, id))
    }

    override suspend fun cantidadGuardada() = dao.contar()

    override suspend fun cantidadFavoritos() = dao.contarFavoritos(uid)

    override suspend fun limpiarCache() = dao.borrarTodo()
}
