package com.salazar.cafetecsup.data.repository

import com.salazar.cafetecsup.data.local.CafeDatabase
import com.salazar.cafetecsup.data.local.dao.PagoResumen
import com.salazar.cafetecsup.data.local.dao.ProductoVendido
import com.salazar.cafetecsup.data.local.entity.DetallePedido
import com.salazar.cafetecsup.data.local.entity.Pedido
import com.salazar.cafetecsup.data.local.entity.Producto
import com.salazar.cafetecsup.data.local.entity.Usuario
import com.salazar.cafetecsup.data.local.relation.PedidoConDetalle
import com.salazar.cafetecsup.model.EstadoPedido
import com.salazar.cafetecsup.model.ItemCarrito
import kotlinx.coroutines.flow.Flow

class CafeRepositoryRoom(
    private val db: CafeDatabase
) : CafeRepository {

    //USUARIOS

    override suspend fun login(correo: String, clave: String): Usuario? {
        return db.usuarioDao().login(correo, clave)
    }

    //PRODUCTOS

    override fun observarProductos(): Flow<List<Producto>> {
        return db.productoDao().observarTodos()
    }

    override suspend fun guardarProducto(p: Producto) {
        db.productoDao().insertar(p)
    }

    override suspend fun eliminarProducto(p: Producto) {
        db.productoDao().eliminar(p)
    }

    override suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean) {
        val producto = db.productoDao().obtenerPorId(id)
        if (producto != null) {
            db.productoDao().actualizar(producto.copy(disponible = disponible))
        }
    }

    //PEDIDOS

    override suspend fun crearPedido(
        clienteId: Int,
        items: List<ItemCarrito>,
        horaRecojo: String,
        pago: String,
        nota: String
    ): Long {
        val total = items.sumOf { it.producto.precio * it.cantidad }

        val pedido = Pedido(
            clienteId = clienteId,
            total = total,
            estado = EstadoPedido.RECIBIDO.name,
            horaRecojo = horaRecojo,
            pago = pago,
            nota = nota,
            fecha = System.currentTimeMillis()
        )

        val detalles = items.map { item ->
            DetallePedido(
                pedidoId = 0,
                productoId = item.producto.id,
                nombreProducto = item.producto.nombre,
                cantidad = item.cantidad,
                precioUnitario = item.producto.precio
            )
        }

        return db.pedidoDao().crearPedido(pedido, detalles)
    }

    override fun observarPedidosCliente(clienteId: Int): Flow<List<PedidoConDetalle>> {
        return db.pedidoDao().observarPorCliente(clienteId)
    }

    override fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>> {
        return db.pedidoDao().observarPorEstado(estado.name)
    }

    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> {
        return db.pedidoDao().observarUno(id)
    }

    override suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido) {
        db.pedidoDao().cambiarEstado(pedidoId, estado.name)
    }

    //PANEL DE VENTAS

    override fun observarVentasHoy(inicioDia: Long): Flow<Double> {
        return db.pedidoDao().ventasDesde(inicioDia)
    }

    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> {
        return db.pedidoDao().cantidadPorEstado(estado.name)
    }

    override fun observarProductoEstrella(inicioDia: Long): Flow<ProductoVendido?> {
        return db.pedidoDao().productoMasVendido(inicioDia)
    }

    override fun observarPagosPorMetodo(inicioDia: Long): Flow<List<PagoResumen>> {
        return db.pedidoDao().pagosPorMetodo(inicioDia)
    }
}