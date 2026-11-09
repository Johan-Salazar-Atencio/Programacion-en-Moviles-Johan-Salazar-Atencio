package com.salazar.pokemon.ui

import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun mensajeDeError(e: Throwable): String = when (e) {
    is SocketTimeoutException -> "La conexión tardó demasiado, intenta de nuevo"
    is IOException -> "No hay conexión a internet"
    is HttpException -> "El servidor respondió con error ${e.code()}"
    is SerializationException -> "La respuesta del servidor no es válida"
    else -> "Ocurrió un error inesperado"
}
