package com.tecsup.mibodega.ui.cliente.screens.terminos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.BodegaTheme

@Composable
fun TerminosScreen(
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Términos y Condiciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            SeccionTermino(
                titulo = "1. Aceptación de los términos",
                contenido = "Al utilizar la aplicación Mi Bodega, usted acepta estos términos y condiciones en su totalidad. Si no está de acuerdo con alguna parte de estos términos, le recomendamos no utilizar la aplicación."
            )
            Spacer(Modifier.height(12.dp))

            SeccionTermino(
                titulo = "2. Uso de la aplicación",
                contenido = "La aplicación Mi Bodega está destinada a facilitar la compra de productos de primera necesidad. El usuario se compromete a utilizar la aplicación de manera responsable y conforme a las leyes vigentes."
            )
            Spacer(Modifier.height(12.dp))

            SeccionTermino(
                titulo = "3. Registro de cuenta",
                contenido = "Para realizar compras, el usuario debe crear una cuenta proporcionando información veraz y actualizada. El usuario es responsable de mantener la confidencialidad de sus credenciales de acceso."
            )
            Spacer(Modifier.height(12.dp))

            SeccionTermino(
                titulo = "4. Entrega de productos",
                contenido = "Los tiempos de entrega son estimados y pueden variar según la disponibilidad y la zona de entrega. Mi Bodega no se responsabiliza por retrasos causados por factores externos."
            )
            Spacer(Modifier.height(12.dp))

            SeccionTermino(
                titulo = "5. Modificaciones",
                contenido = "Nos reservamos el derecho de modificar estos términos en cualquier momento. Los cambios serán notificados a través de la aplicación."
            )
        }
    }
}

@Composable
private fun SeccionTermino(titulo: String, contenido: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = contenido,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TerminosPreview() {
    BodegaTheme {
        TerminosScreen(onVolver = {})
    }
}
