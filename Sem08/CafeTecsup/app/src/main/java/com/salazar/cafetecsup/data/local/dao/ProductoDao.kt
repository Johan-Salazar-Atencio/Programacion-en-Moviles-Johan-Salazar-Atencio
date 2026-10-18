package com.salazar.cafetecsup.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.salazar.cafetecsup.data.local.entity.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun observarTodos(): Flow<List<Producto>>
    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    fun observarUno(id: Int): Flow<Producto?>
    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): Producto?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: Producto)
    @Update
    suspend fun actualizar(producto: Producto)
    @Delete
    suspend fun eliminar(producto: Producto)
}