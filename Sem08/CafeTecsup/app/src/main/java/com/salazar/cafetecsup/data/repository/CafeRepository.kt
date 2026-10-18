package com.salazar.cafetecsup.data.repository

import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.local.entity.Usuario
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import com.salazar.cafetecsup.model.EstadoPedido
import com.salazar.cafetecsup.model.ItemCarrito
import kotlinx.coroutines.flow.Flow

interface CafeRepository {

    // Usuarios
    suspend fun login(correo: String, clave: String): Usuario?

    // Productos
    fun observarProductos(): Flow<List<Producto>>
    suspend fun guardarProducto(p: Producto)
    suspend fun eliminarProducto(p: Producto)
    suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean)

    // Pedidos
    suspend fun crearPedido(
        clienteId: Int,
        items: List<ItemCarrito>,
        horaRecojo: String,
        pago: String,
        nota: String
    ): Long

    fun observarPedidosCliente(clienteId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido)

    // Panel
    fun observarVentasHoy(inicioDia: Long): Flow<Double>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
}