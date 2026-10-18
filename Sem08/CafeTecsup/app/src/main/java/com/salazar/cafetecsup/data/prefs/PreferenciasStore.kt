package com.salazar.cafetecsup.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "ajustes")

class PreferenciasStore(private val context: Context) {

    companion object {
        val PERFIL_ACTIVO = stringPreferencesKey("perfil_activo")
        val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
        val RECIBIR_PEDIDOS = booleanPreferencesKey("recibir_pedidos")
    }

    val perfilActivo: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PERFIL_ACTIVO] ?: ""
    }

    val modoOscuro: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[MODO_OSCURO] ?: false
    }

    val recibirPedidos: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[RECIBIR_PEDIDOS] ?: true
    }

    suspend fun guardarPerfil(rol: String) {
        context.dataStore.edit { preferences ->
            preferences[PERFIL_ACTIVO] = rol
        }
    }

    suspend fun guardarModoOscuro(b: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MODO_OSCURO] = b
        }
    }

    suspend fun guardarRecibirPedidos(b: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[RECIBIR_PEDIDOS] = b
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { preferences ->
            preferences[PERFIL_ACTIVO] = ""
        }
    }
}