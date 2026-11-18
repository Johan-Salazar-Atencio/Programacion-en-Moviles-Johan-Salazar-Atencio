package com.tecsup.campusgps.data

interface UltimoLugarRepository {
    suspend fun leer(): Int?
    suspend fun guardar(id: Int)
}
