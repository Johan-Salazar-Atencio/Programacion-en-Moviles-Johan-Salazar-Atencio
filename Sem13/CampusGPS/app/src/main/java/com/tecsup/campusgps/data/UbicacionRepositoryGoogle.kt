package com.tecsup.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tecsup.campusgps.model.Posicion
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(contexto: Context) : UbicacionRepository {

    private val cliente = LocationServices.getFusedLocationProviderClient(contexto)

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> = try {
        val ubicacion = cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        if (ubicacion != null) {
            Result.success(Posicion(ubicacion.latitude, ubicacion.longitude))
        } else {
            Result.failure(IllegalStateException("No se pudo obtener la ubicación"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
