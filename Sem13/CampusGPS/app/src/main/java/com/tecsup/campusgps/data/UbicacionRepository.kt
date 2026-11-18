package com.tecsup.campusgps.data

import com.tecsup.campusgps.model.Posicion

class UbicacionNoDisponibleException : Exception()

interface UbicacionRepository {
    fun playServicesDisponible(): Boolean
    suspend fun obtenerPosicion(): Result<Posicion>
}
