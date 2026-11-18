package com.tecsup.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tecsup.campusgps.model.Posicion
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(private val contexto: Context) : UbicacionRepository {

    private val cliente = LocationServices.getFusedLocationProviderClient(contexto)

    override fun playServicesDisponible(): Boolean =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(contexto) == ConnectionResult.SUCCESS

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> = try {
        val ubicacion = cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        if (ubicacion != null) {
            Result.success(Posicion(ubicacion.latitude, ubicacion.longitude))
        } else {
            Result.failure(UbicacionNoDisponibleException())
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
