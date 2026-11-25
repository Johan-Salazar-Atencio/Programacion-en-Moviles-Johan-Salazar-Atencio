package com.salazar.avisoscampus.notificacion

import android.content.Context
import com.salazar.avisoscampus.model.Aviso

object Notificador {

    const val CANAL = "avisos"

    fun crearCanal(context: Context) {
        // TODO: si SDK_INT >= O, crear NotificationChannel y registrarlo
    }

    fun mostrar(context: Context, aviso: Aviso): Boolean {
        // TODO 1: si no tienePermiso(context) -> return false
        // TODO 2: construir la notificacion con NotificationCompat.Builder
        // TODO 3: NotificationManagerCompat.from(context).notify(aviso.id, ...)
        return true
    }
}
