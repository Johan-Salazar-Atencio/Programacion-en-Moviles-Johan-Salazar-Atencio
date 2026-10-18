package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf("CLIENTE") }

    val error by viewModel.error.collectAsState()
    val usuarioActual by viewModel.usuarioActual.collectAsState()

    val colorPrincipal = if (rolSeleccionado == "CLIENTE") Color(0xFFE65100) else Color(0xFF3F51B5)

    LaunchedEffect(usuarioActual) {
        usuarioActual?.let { onLoginSuccess(it.rol) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Ingresa para continuar", color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
                selected = rolSeleccionado == "CLIENTE",
                onClick = { rolSeleccionado = "CLIENTE" },
                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                colors = SegmentedButtonDefaults.colors(activeContainerColor = colorPrincipal, activeContentColor = Color.White)
            ) { Text("Estudiante") }
            SegmentedButton(
                selected = rolSeleccionado == "ADMIN",
                onClick = { rolSeleccionado = "ADMIN" },
                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                colors = SegmentedButtonDefaults.colors(activeContainerColor = colorPrincipal, activeContentColor = Color.White)
            ) { Text("Administrador") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(correo, clave, rolSeleccionado) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorPrincipal)
        ) {
            Text("Ingresar", fontSize = 16.sp)
        }
    }
}