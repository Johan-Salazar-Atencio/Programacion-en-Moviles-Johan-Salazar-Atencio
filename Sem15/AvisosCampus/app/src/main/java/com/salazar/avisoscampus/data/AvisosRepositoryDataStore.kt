package com.salazar.avisoscampus.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.salazar.avisoscampus.model.Aviso
import com.salazar.avisoscampus.model.Origen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

// Recibe el DataStore ya creado: el Repository no usa Context
class AvisosRepositoryDataStore(
    private val almacen: DataStore<Preferences>
) : AvisosRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _avisos = MutableStateFlow<List<Aviso>>(emptyList())
    override val avisos: StateFlow<List<Aviso>> = _avisos.asStateFlow()
    private val siguiente = AtomicInteger(1)

    init {
        // Carga en segundo plano: no bloquea el hilo principal
        scope.launch { cargar() }
    }

    override fun registrar(titulo: String, texto: String, origen: Origen): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val aviso = Aviso(siguiente.getAndIncrement(), titulo, texto, origen, hora)
        _avisos.update { listOf(aviso) + it } // lo más reciente arriba
        scope.launch { guardar() }
        return aviso
    }

    private suspend fun cargar() {
        val guardados = deTexto(almacen.data.first()[CLAVE])
        // Si se registró algo mientras cargaba, queda arriba de lo guardado
        _avisos.update { recientes -> recientes + guardados }
        // Los ids nuevos continúan después del mayor id guardado
        val mayor = guardados.maxOfOrNull { it.id } ?: 0
        siguiente.updateAndGet { maxOf(it, mayor + 1) }
    }

    private suspend fun guardar() {
        // La lista se lee dentro de edit: las escrituras van en orden y la última deja lo más nuevo
        almacen.edit { it[CLAVE] = aTexto(_avisos.value.take(MAXIMO)) }
    }

    private fun aTexto(lista: List<Aviso>): String {
        val arreglo = JSONArray()
        lista.forEach { a ->
            arreglo.put(
                JSONObject()
                    .put("id", a.id)
                    .put("titulo", a.titulo)
                    .put("texto", a.texto)
                    .put("origen", a.origen.name)
                    .put("hora", a.hora)
            )
        }
        return arreglo.toString()
    }

    private fun deTexto(texto: String?): List<Aviso> {
        if (texto.isNullOrEmpty()) return emptyList()
        return try {
            val arreglo = JSONArray(texto)
            (0 until arreglo.length()).map { i ->
                val o = arreglo.getJSONObject(i)
                Aviso(
                    id = o.getInt("id"),
                    titulo = o.getString("titulo"),
                    texto = o.getString("texto"),
                    origen = Origen.entries.firstOrNull { it.name == o.getString("origen") }
                        ?: Origen.BOTON,
                    hora = o.getString("hora")
                )
            }
        } catch (e: JSONException) {
            emptyList() // datos corruptos: se empieza con el historial vacío
        }
    }

    private companion object {
        val CLAVE = stringPreferencesKey("historial")
        const val MAXIMO = 50
    }
}
