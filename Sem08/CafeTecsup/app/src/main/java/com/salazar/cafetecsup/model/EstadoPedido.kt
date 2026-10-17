package com.salazar.cafetecsup.model

import com.salazar.cafetecsup.data.local.entity.Producto

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1
)