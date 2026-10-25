package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.viewmodel.SesionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: SesionViewModel,
    onLoginSuccess: (String) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var rolSeleccionado by rememberSaveable { mutableStateOf("CLIENTE") }

    val error by viewModel.error.collectAsState()
    val usuarioActual by viewModel.usuarioActual.collectAsState()

    val colorPrincipal = if (rolSeleccionado == "CLIENTE") colorScheme.secondary else colorScheme.primary

    LaunchedEffect(usuarioActual) {
        usuarioActual?.let { onLoginSuccess(it.rol) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorScheme.background)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorScheme.onBackground)
        Text("Ingresa para continuar", color = colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 24.dp))

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
                selected = rolSeleccionado == "CLIENTE",
                onClick = { rolSeleccionado = "CLIENTE" },
                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = colorPrincipal,
                    activeContentColor = colorScheme.onPrimary
                )
            ) { Text("Estudiante") }
            SegmentedButton(
                selected = rolSeleccionado == "ADMIN",
                onClick = { rolSeleccionado = "ADMIN" },
                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = colorPrincipal,
                    activeContentColor = colorScheme.onPrimary
                )
            ) { Text("Administrador") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo", color = colorScheme.onSurfaceVariant) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surfaceVariant,
                unfocusedContainerColor = colorScheme.surfaceVariant,
                focusedBorderColor = colorPrincipal,
                unfocusedBorderColor = colorScheme.outline,
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Contraseña", color = colorScheme.onSurfaceVariant) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surfaceVariant,
                unfocusedContainerColor = colorScheme.surfaceVariant,
                focusedBorderColor = colorPrincipal,
                unfocusedBorderColor = colorScheme.outline,
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface
            ),
            singleLine = true
        )

        if (error.isNotEmpty()) {
            Text(error, color = colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(correo, clave, rolSeleccionado) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorPrincipal,
                contentColor = colorScheme.onPrimary
            )
        ) {
            Text("Ingresar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colorScheme.onPrimary)
        }
    }
}
