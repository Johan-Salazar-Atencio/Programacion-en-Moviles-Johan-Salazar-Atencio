package com.tecsup.mibodega.ui.cliente.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

const val USUARIO_FIJO = "admin"
const val PASSWORD_FIJO = "1234"

@Composable
fun LoginScreen(
    onVolver: () -> Unit,
    onLoginExitoso: (usuario: String) -> Unit
) {
    var usuarioInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    var errorUsuario by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }
    var mensajeErrorGlobal by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
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
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(48.dp))
        }

        Spacer(Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = VerdeBodega,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Ingresa tus credenciales",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Credenciales fijas: admin / 1234",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        CampoTexto(
            etiqueta = "Usuario",
            valor = usuarioInput,
            onValorCambia = {
                usuarioInput = it
                errorUsuario = false
                mensajeErrorGlobal = null
            },
            placeholder = "admin",
            esError = errorUsuario,
            mensajeError = if (errorUsuario && usuarioInput.isBlank()) "El usuario no puede estar vacío" else null
        )

        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            valor = passwordInput,
            onValorCambia = {
                passwordInput = it
                errorPassword = false
                mensajeErrorGlobal = null
            },
            placeholder = "••••",
            teclado = KeyboardType.Password,
            isPassword = true,
            esError = errorPassword,
            mensajeError = if (errorPassword && passwordInput.isBlank()) "La contraseña no puede estar vacía" else null
        )

        if (mensajeErrorGlobal != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = mensajeErrorGlobal!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Iniciar sesión",
            onClick = {
                val esUsuarioVacio = usuarioInput.isBlank()
                val esPasswordVacio = passwordInput.isBlank()

                if (esUsuarioVacio || esPasswordVacio) {
                    errorUsuario = esUsuarioVacio
                    errorPassword = esPasswordVacio
                    mensajeErrorGlobal = "Por favor, completa todos los campos"
                } else if (usuarioInput.trim() == USUARIO_FIJO && passwordInput.trim() == PASSWORD_FIJO) {
                    onLoginExitoso(usuarioInput.trim())
                } else {
                    errorUsuario = true
                    errorPassword = true
                    mensajeErrorGlobal = "Usuario o contraseña incorrectos"
                }
            }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    BodegaTheme {
        LoginScreen(onVolver = {}, onLoginExitoso = {})
    }
}
