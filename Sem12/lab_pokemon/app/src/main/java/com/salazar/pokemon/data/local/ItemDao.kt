package com.salazar.pokemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query(
        """
        SELECT i.id AS id, i.titulo AS titulo, i.subtitulo AS subtitulo, i.imagenUrl AS imagenUrl,
               (f.itemId IS NOT NULL) AS esFavorito
        FROM items i
        LEFT JOIN favoritos f ON f.itemId = i.id AND f.uid = :uid
        ORDER BY i.titulo
        """
    )
    fun todos(uid: String): Flow<List<ItemConFavorito>>

    @Query(
        """
        SELECT i.id AS id, i.titulo AS titulo, i.subtitulo AS subtitulo, i.imagenUrl AS imagenUrl,
               1 AS esFavorito
        FROM items i
        INNER JOIN favoritos f ON f.itemId = i.id
        WHERE f.uid = :uid
        ORDER BY i.titulo
        """
    )
    fun favoritos(uid: String): Flow<List<ItemConFavorito>>

    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE uid = :uid AND itemId = :id)")
    suspend fun esFavorito(uid: String, id: Int): Boolean

    @Query("SELECT COUNT(*) FROM items")
    suspend fun contar(): Int

    @Query(
        """
        SELECT COUNT(*) FROM favoritos f
        INNER JOIN items i ON i.id = f.itemId
        WHERE f.uid = :uid
        """
    )
    suspend fun contarFavoritos(uid: String): Int

    @Upsert
    suspend fun guardarTodos(items: List<ItemEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun agregarFavorito(favorito: FavoritoEntity)

    @Query("DELETE FROM favoritos WHERE uid = :uid AND itemId = :id")
    suspend fun quitarFavorito(uid: String, id: Int): Int

    // los favoritos no se borran: pertenecen a cada cuenta y vuelven a verse al sincronizar
    @Query("DELETE FROM items")
    suspend fun borrarTodo()
}
