package com.salazar.cafetecsup.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.salazar.cafetecsup.data.local.entity.DetallePedido
import com.salazar.cafetecsup.data.local.entity.Pedido
import com.salazar.cafetecsup.data.local.entity.Usuario

data class PedidoConDetalle(
    @Embedded
    val pedido: Pedido,

    @Relation(
        parentColumn = "clienteId",
        entityColumn = "id"
    )
    val cliente: Usuario,

    @Relation(
        parentColumn = "id",
        entityColumn = "pedidoId"
    )
    val detalles: List<DetallePedido>
)