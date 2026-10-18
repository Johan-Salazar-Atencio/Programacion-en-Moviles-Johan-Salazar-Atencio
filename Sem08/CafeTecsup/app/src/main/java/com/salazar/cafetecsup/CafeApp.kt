package com.salazar.cafetecsup

import android.app.Application
import com.salazar.cafetecsup.data.local.CafeDatabase
import com.salazar.cafetecsup.data.local.DatosIniciales
import com.salazar.cafetecsup.data.repository.CafeRepository
import com.salazar.cafetecsup.data.repository.CafeRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CafeApp : Application() {

    val database by lazy { CafeDatabase.obtener(this) }
    val repository: CafeRepository by lazy { CafeRepositoryRoom(database) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            DatosIniciales.sembrar(database)
        }
    }
}