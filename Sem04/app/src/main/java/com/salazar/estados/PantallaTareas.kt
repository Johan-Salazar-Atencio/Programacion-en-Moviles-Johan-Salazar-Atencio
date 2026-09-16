package com.salazar.estados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * CLASE STATE HOLDER (POO - Gestor de Estado de Lista de Tareas)
 */
class TareasStateHolder {
    var textoTarea by mutableStateOf("")
        private set

    private var contadorId = 1

    val listaTareas = mutableStateListOf<Tarea>()

    val totalTareas: Int
        get() = listaTareas.size

    fun actualizarTexto(nuevoTexto: String) {
        textoTarea = nuevoTexto
    }

    fun agregarTarea() {
        if (textoTarea.isNotBlank()) {
            listaTareas.add(
                Tarea(
                    id = contadorId++,
                    nombre = textoTarea.trim()
                )
            )
            textoTarea = ""
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        listaTareas.remove(tarea)
    }

    fun cambiarEstadoTarea(tarea: Tarea, completada: Boolean) {
        val index = listaTareas.indexOf(tarea)
        if (index != -1) {
            listaTareas[index] = listaTareas[index].copy(completada = completada)
        }
    }
}

/**
 * COMPOSABLE PRINCIPAL
 */
@Composable
fun PantallaTareas(
    stateHolder: TareasStateHolder = remember { TareasStateHolder() }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de tareas - Tecsup",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = stateHolder.textoTarea,
            onValueChange = { stateHolder.actualizarTexto(it) },
            label = { Text("Ingrese una tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { stateHolder.agregarTarea() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total de tareas: ${stateHolder.totalTareas}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(stateHolder.listaTareas, key = { it.id }) { tarea ->
                ItemTarea(
                    tarea = tarea,
                    onEliminar = { stateHolder.eliminarTarea(tarea) },
                    onCambiarEstado = { completada ->
                        stateHolder.cambiarEstadoTarea(tarea, completada)
                    }
                )
            }
        }
    }
}