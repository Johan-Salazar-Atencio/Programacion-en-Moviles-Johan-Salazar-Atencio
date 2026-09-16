package com.salazar.estados

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * CLASE STATE HOLDER (Encapsulamiento en POO)
 * Encapsula la lógica de negocio y los estados de la temperatura.
 */
class TemperatureStateHolder(
    temperaturaInicial: Int = 20
) {
    // Estado privado (Abstracción/Encapsulamiento): solo la clase puede modificarlo directamente
    var temperatura by mutableStateOf(temperaturaInicial)
        private set

    // Propiedad calculada que aplica la lógica de presentación según el estado actual
    val colorTexto: Color
        get() = when {
            temperatura > 30 -> Color.Red
            temperatura < 10 -> Color.Blue
            else -> Color.Unspecified
        }

    // Métodos (Comportamientos del objeto)
    fun subir() {
        temperatura++
    }

    fun bajar() {
        temperatura--
    }

    fun resetear() {
        temperatura = 20
    }
}

/**
 * COMPOSABLE (Vista limpia)
 * Solo consume el objeto State Holder y dibuja la UI.
 */
@Composable
fun TemperatureDisplay(
    // Inyección de dependencias simple: se instancia o pasa el objeto manipulador de estado
    stateHolder: TemperatureStateHolder = remember { TemperatureStateHolder() }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Temperatura: ${stateHolder.temperatura}°C",
            style = MaterialTheme.typography.headlineMedium,
            color = stateHolder.colorTexto
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { stateHolder.subir() }) { Text("Subir") }
            Button(onClick = { stateHolder.bajar() }) { Text("Bajar") }
            Button(onClick = { stateHolder.resetear() }) { Text("Resetear") }
        }
    }
}