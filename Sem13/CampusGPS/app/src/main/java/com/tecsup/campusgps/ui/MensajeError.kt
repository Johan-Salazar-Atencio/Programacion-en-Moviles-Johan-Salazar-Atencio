package com.tecsup.campusgps.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.campusgps.model.TipoError
import com.tecsup.campusgps.model.UbicacionUi

@Composable
fun MensajeError(
    error: UbicacionUi.Error,
    onReintentar: () -> Unit,
    onAbrirAjustes: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        color = MaterialTheme.colorScheme.errorContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(error.mensaje, color = MaterialTheme.colorScheme.onErrorContainer)
            when (error.tipo) {
                TipoError.SIN_PERMISO -> Button(
                    onClick = onAbrirAjustes,
                    modifier = Modifier.padding(top = 8.dp)
                ) { Text("Abrir ajustes") }
                TipoError.GPS_APAGADO, TipoError.OTRO -> Button(
                    onClick = onReintentar,
                    modifier = Modifier.padding(top = 8.dp)
                ) { Text("Reintentar") }
                TipoError.SIN_PLAY_SERVICES -> Unit
            }
        }
    }
}
