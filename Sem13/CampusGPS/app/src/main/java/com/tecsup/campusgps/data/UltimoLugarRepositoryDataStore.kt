package com.tecsup.campusgps.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "campusgps")

class UltimoLugarRepositoryDataStore(private val contexto: Context) : UltimoLugarRepository {

    private val clave = intPreferencesKey("ultimo_lugar")

    override suspend fun leer(): Int? =
        contexto.dataStore.data.map { it[clave] }.first()

    override suspend fun guardar(id: Int) {
        contexto.dataStore.edit { it[clave] = id }
    }
}
