package com.salazar.avisoscampus

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.salazar.avisoscampus.data.AvisosRepository
import com.salazar.avisoscampus.data.AvisosRepositoryDataStore
import com.salazar.avisoscampus.notificacion.Notificador

// Un solo DataStore por archivo en todo el proceso
private val Context.almacenAvisos by preferencesDataStore(name = "avisos")

class AvisosApp : Application() {

    // El Repository recibe el DataStore, no el Context
    val repositorio: AvisosRepository by lazy { AvisosRepositoryDataStore(almacenAvisos) }

    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
