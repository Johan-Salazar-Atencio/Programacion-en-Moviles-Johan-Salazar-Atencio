package com.salazar.avisoscampus.notificacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.salazar.avisoscampus.MainActivity
import com.salazar.avisoscampus.R
import com.salazar.avisoscampus.model.Aviso
import com.salazar.avisoscampus.model.Origen

object Notificador {

    const val CANAL = "avisos"
    const val CANAL_URGENTE = "urgentes"

    fun crearCanal(context: Context) {
        // Los canales existen desde Android 8 (O)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(
                NotificationChannel(CANAL, "Avisos", NotificationManager.IMPORTANCE_DEFAULT)
            )
            // IMPORTANCE_HIGH: la notificación aparece como emergente
            manager.createNotificationChannel(
                NotificationChannel(CANAL_URGENTE, "Urgentes", NotificationManager.IMPORTANCE_HIGH)
            )
        }
    }

    // El permiso solo se pide desde Android 13 (API 33)
    fun tienePermiso(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    // El permiso se comprueba antes de notify, por eso se silencia el aviso del IDE
    @SuppressLint("MissingPermission")
    fun mostrar(context: Context, aviso: Aviso): Boolean {
        if (!tienePermiso(context)) return false

        // Al tocar la notificación se abre la app; FLAG_IMMUTABLE porque el intent no cambia
        val intent = Intent(context, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // El recordatorio va al canal urgente; el resto, al canal normal
        val urgente = aviso.origen == Origen.RECORDATORIO
        val canal = if (urgente) CANAL_URGENTE else CANAL
        // La prioridad del builder solo cuenta en Android 7 e inferiores; desde 8 manda el canal
        val prioridad = if (urgente) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_DEFAULT

        val notificacion = NotificationCompat.Builder(context, canal)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            .setPriority(prioridad)
            .setContentIntent(pendiente)
            .setAutoCancel(true)
            .build()

        // El id del aviso hace que cada notificación sea distinta
        NotificationManagerCompat.from(context).notify(aviso.id, notificacion)
        return true
    }
}
