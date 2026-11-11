package com.salazar.pokemon.fakes

import com.salazar.pokemon.data.local.FavoritoEntity
import com.salazar.pokemon.data.local.ItemConFavorito
import com.salazar.pokemon.data.local.ItemDao
import com.salazar.pokemon.data.local.ItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeItemDao : ItemDao {
    private val items = MutableStateFlow<List<ItemEntity>>(emptyList())
    private val favoritos = MutableStateFlow<Set<FavoritoEntity>>(emptySet())

    private fun combinar(uid: String, lista: List<ItemEntity>, favs: Set<FavoritoEntity>) =
        lista.sortedBy { it.titulo }.map {
            ItemConFavorito(it.id, it.titulo, it.subtitulo, it.imagenUrl, FavoritoEntity(uid, it.id) in favs)
        }

    override fun todos(uid: String): Flow<List<ItemConFavorito>> =
        kotlinx.coroutines.flow.combine(items, favoritos) { l, f -> combinar(uid, l, f) }

    override fun favoritos(uid: String): Flow<List<ItemConFavorito>> =
        todos(uid).map { lista -> lista.filter { it.esFavorito } }

    override suspend fun esFavorito(uid: String, id: Int) = FavoritoEntity(uid, id) in favoritos.value

    override suspend fun contar() = items.value.size

    override suspend fun contarFavoritos(uid: String) =
        favoritos.value.count { it.uid == uid && items.value.any { i -> i.id == it.itemId } }

    override suspend fun guardarTodos(items: List<ItemEntity>) {
        val porId = this.items.value.associateBy { it.id }.toMutableMap()
        items.forEach { porId[it.id] = it }
        this.items.value = porId.values.toList()
    }

    override suspend fun agregarFavorito(favorito: FavoritoEntity) {
        favoritos.value = favoritos.value + favorito
    }

    override suspend fun quitarFavorito(uid: String, id: Int): Int {
        val existe = FavoritoEntity(uid, id) in favoritos.value
        favoritos.value = favoritos.value - FavoritoEntity(uid, id)
        return if (existe) 1 else 0
    }

    override suspend fun borrarTodo() {
        items.value = emptyList()
    }
}
