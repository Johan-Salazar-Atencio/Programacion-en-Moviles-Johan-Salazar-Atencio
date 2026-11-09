package com.salazar.pokemon.ui

import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class MensajesDeErrorTest {

    @Test
    fun sinInternetAvisaQueNoHayConexion() {
        assertEquals("No hay conexión a internet", mensajeDeError(IOException()))
    }

    @Test
    fun unTiempoDeEsperaAgotadoTieneSuPropioMensaje() {
        assertEquals(
            "La conexión tardó demasiado, intenta de nuevo",
            mensajeDeError(SocketTimeoutException())
        )
    }

    @Test
    fun unErrorDelServidorIncluyeElCodigo() {
        val respuesta = Response.error<Any>(503, "".toResponseBody())

        assertEquals("El servidor respondió con error 503", mensajeDeError(HttpException(respuesta)))
    }

    @Test
    fun unaRespuestaInesperadaSeDistingueDelResto() {
        assertEquals(
            "La respuesta del servidor no es válida",
            mensajeDeError(SerializationException("json"))
        )
    }

    @Test
    fun cualquierOtroErrorUsaElMensajeGeneral() {
        assertEquals("Ocurrió un error inesperado", mensajeDeError(IllegalStateException()))
    }
}
