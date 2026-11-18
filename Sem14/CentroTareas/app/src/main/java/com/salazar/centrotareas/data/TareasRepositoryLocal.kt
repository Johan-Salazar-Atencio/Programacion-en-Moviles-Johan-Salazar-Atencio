package com.salazar.centrotareas.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException

class TareasRepositoryLocal : TareasRepository {
    override fun progresoTarea(): Flow<Float> = flow {
        for (i in 1..100) {
            if (i == 1) Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
            delay(40)
            emit(i / 100f)
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Johan Salazar (ficticio)"
    }

    override suspend fun cargarPedidos(simularError: Boolean, lento: Boolean): List<String> {
        delay(if (lento) 8000 else 3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    override suspend fun contarPrimos(hasta: Int): Int = withContext(Dispatchers.Default) {
        var cantidad = 0
        for (n in 2..hasta) {
            ensureActive()
            if (esPrimo(n)) cantidad++
        }
        cantidad
    }

    private fun esPrimo(n: Int): Boolean {
        var d = 2
        while (d.toLong() * d <= n) {
            if (n % d == 0) return false
            d++
        }
        return true
    }
}
