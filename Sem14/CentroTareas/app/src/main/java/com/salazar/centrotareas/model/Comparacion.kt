package com.salazar.centrotareas.model

// Tiempos acumulados (ms) de cada modo de carga
data class Comparacion(
    val unaPorUna: List<Long> = emptyList(),
    val aLaVez: List<Long> = emptyList()
)
