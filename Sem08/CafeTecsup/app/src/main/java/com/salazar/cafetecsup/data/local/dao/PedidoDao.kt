package com.salazar.cafetecsup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.salazar.cafetecsup.data.local.dao.PagoResumen
import com.salazar.cafetecsup.data.local.dao.ProductoVendido
import com.salazar.cafetecsup.data.local.entity.DetallePedido
import com.salazar.cafetecsup.data.local.entity.Pedido
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    //escritura

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedido(pedido: Pedido): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalles(detalles: List<DetallePedido>)

    @Query("UPDATE pedidos SET estado = :estado WHERE id = :id")
    suspend fun cambiarEstado(id: Int, estado: String)

    //Crear
    @Transaction
    suspend fun crearPedido(pedido: Pedido, detalles: List<DetallePedido>): Long {
        val pedidoId = insertarPedido(pedido)
        val detallesConId = detalles.map { it.copy(pedidoId = pedidoId.toInt()) }
        insertarDetalles(detallesConId)
        return pedidoId
    }

    // lectura

    @Transaction
    @Query("SELECT * FROM pedidos WHERE clienteId = :clienteId ORDER BY fecha DESC")
    fun observarPorCliente(clienteId: Int): Flow<List<PedidoConDetalle>>

    @Transaction
    @Query("SELECT * FROM pedidos WHERE estado = :estado ORDER BY fecha DESC")
    fun observarPorEstado(estado: String): Flow<List<PedidoConDetalle>>

    @Transaction
    @Query("SELECT * FROM pedidos WHERE id = :id LIMIT 1")
    fun observarUno(id: Int): Flow<PedidoConDetalle?>

    // panel

    @Query("SELECT COALESCE(SUM(total), 0.0) FROM pedidos WHERE fecha >= :inicio AND estado != 'CANCELADO'")
    fun ventasDesde(inicio: Long): Flow<Double>

    @Query("SELECT COUNT(*) FROM pedidos WHERE estado = :estado")
    fun cantidadPorEstado(estado: String): Flow<Int>

    // Reporte diario

    @Query(
        "SELECT nombreProducto AS nombre, SUM(cantidad) AS cantidad " +
                "FROM detalle_pedido dp " +
                "INNER JOIN pedidos p ON p.id = dp.pedidoId " +
                "WHERE p.fecha >= :inicio AND p.estado != 'CANCELADO' " +
                "GROUP BY nombreProducto " +
                "ORDER BY cantidad DESC " +
                "LIMIT 1"
    )
    fun productoMasVendido(inicio: Long): Flow<ProductoVendido?>

    @Query(
        "SELECT pago AS metodo, COALESCE(SUM(total), 0.0) AS monto " +
                "FROM pedidos " +
                "WHERE fecha >= :inicio AND estado != 'CANCELADO' " +
                "GROUP BY pago " +
                "ORDER BY monto DESC"
    )
    fun pagosPorMetodo(inicio: Long): Flow<List<PagoResumen>>
}