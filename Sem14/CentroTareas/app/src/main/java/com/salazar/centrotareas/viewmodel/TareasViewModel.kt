package com.salazar.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.centrotareas.data.TareasRepository
import com.salazar.centrotareas.model.CargaUi
import com.salazar.centrotareas.model.Comparacion
import com.salazar.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    fun tareaLarga() {
        if (_corriendo.value) return
        trabajo = viewModelScope.launch {
            _corriendo.value = true
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.progresoTarea().collect { _progreso.value = it }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()

    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (_primos.value == PrimosUi.Calculando) return
        trabajoPrimos = viewModelScope.launch {
            _primos.value = PrimosUi.Calculando
            val inicio = System.currentTimeMillis()
            try {
                val cantidad = repo.contarPrimos(hasta)
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    fun cargarUnaPorUna() = cargar("Una por una") {
        val perfil = repo.cargarPerfil()
        val pedidos = repo.cargarPedidos(_simularError.value, _simularLentitud.value)
        Pair(perfil, pedidos)
    }

    fun cargarALaVez() = cargar("A la vez") {
        coroutineScope {
            val perfil = async { repo.cargarPerfil() }
            val pedidos = async { repo.cargarPedidos(_simularError.value, _simularLentitud.value) }
            Pair(perfil.await(), pedidos.await())
        }
    }

    private fun cargar(modo: String, bloque: suspend () -> Pair<String, List<String>>) {
        if (_carga.value == CargaUi.Cargando) return
        viewModelScope.launch {
            _carga.value = CargaUi.Cargando
            _intento.value = ""
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = conReintentos { withTimeout(LIMITE_MS) { bloque() } }
                val ms = System.currentTimeMillis() - inicio
                _carga.value = CargaUi.Exito(modo, perfil, pedidos.size, ms)
                registrarTiempo(modo, ms)
            } catch (e: TimeoutCancellationException) {
                // Va antes que CancellationException: es una subclase suya
                _carga.value = CargaUi.Error("Tardó demasiado")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

    private val _comparacion = MutableStateFlow(Comparacion())
    val comparacion: StateFlow<Comparacion> = _comparacion.asStateFlow()

    private fun registrarTiempo(modo: String, ms: Long) {
        _comparacion.update { c ->
            if (modo == "Una por una") c.copy(unaPorUna = c.unaPorUna + ms)
            else c.copy(aLaVez = c.aLaVez + ms)
        }
    }

    private val _intento = MutableStateFlow("")
    val intento: StateFlow<String> = _intento.asStateFlow()

    // Solo reintenta ante IOException para no atrapar la cancelación
    private suspend fun <T> conReintentos(bloque: suspend () -> T): T {
        var espera = 500L
        for (n in 1 until MAX_INTENTOS) {
            try {
                return bloque()
            } catch (e: IOException) {
                _intento.value = "Reintentando (${n + 1} de $MAX_INTENTOS)..."
                delay(espera)
                espera *= 2
            }
        }
        return bloque()
    }

    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    private val _simularLentitud = MutableStateFlow(false)
    val simularLentitud: StateFlow<Boolean> = _simularLentitud.asStateFlow()

    fun cambiarSimularLentitud(valor: Boolean) {
        _simularLentitud.value = valor
    }

    private companion object {
        const val LIMITE_MS = 6000L
        const val MAX_INTENTOS = 3
    }
}
