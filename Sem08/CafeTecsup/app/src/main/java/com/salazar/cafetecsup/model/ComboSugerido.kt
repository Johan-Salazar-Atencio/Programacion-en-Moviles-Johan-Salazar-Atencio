package com.salazar.cafetecsup.model

import com.salazar.cafetecsup.data.local.entity.Producto

data class ComboSugerido(
    val items: List<Producto>,
    val total: Double
)
