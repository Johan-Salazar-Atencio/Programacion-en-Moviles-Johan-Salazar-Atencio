package com.salazar.centrotareas.data

import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    fun progresoTarea(): Flow<Float>
    suspend fun contarPrimos(hasta: Int): Int
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean, lento: Boolean = false): List<String>
}
