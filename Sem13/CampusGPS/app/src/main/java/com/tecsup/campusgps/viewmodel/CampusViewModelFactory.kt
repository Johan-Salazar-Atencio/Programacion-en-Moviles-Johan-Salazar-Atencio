package com.tecsup.campusgps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.campusgps.data.LugaresRepositoryLocal
import com.tecsup.campusgps.data.UbicacionRepositoryGoogle
import com.tecsup.campusgps.data.UltimoLugarRepositoryDataStore

class CampusViewModelFactory(private val contexto: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MapaViewModel(
            UbicacionRepositoryGoogle(contexto.applicationContext),
            LugaresRepositoryLocal(),
            UltimoLugarRepositoryDataStore(contexto.applicationContext)
        ) as T
}
