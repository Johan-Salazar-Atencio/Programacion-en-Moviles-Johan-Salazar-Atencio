package com.salazar.pokemon.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.pokemon.ui.UiState

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginExitoso: () -> Unit) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var claveVisible by rememberSaveable { mutableStateOf(false) }
    val hayError = estado is UiState.Error
    val cargando = estado is UiState.Cargando

    LaunchedEffect(estado) {
        if (estado is UiState.Exito) onLoginExitoso()
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Pokebola()
                Text(
                    text = "Pokédex",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Inicia sesión para continuar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    singleLine = true,
                    isError = hayError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = clave,
                    onValueChange = { clave = it },
                    label = { Text("Contraseña") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    trailingIcon = {
                        TextButton(onClick = { claveVisible = !claveVisible }) {
                            Text(if (claveVisible) "Ocultar" else "Ver")
                        }
                    },
                    singleLine = true,
                    isError = hayError,
                    visualTransformation = if (claveVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { viewModel.ingresar(correo, clave) }),
                    modifier = Modifier.fillMaxWidth()
                )

                (estado as? UiState.Error)?.let {
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Filled.Warning, contentDescription = null)
                            Text(it.mensaje, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Button(
                    onClick = { viewModel.ingresar(correo, clave) },
                    enabled = !cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = LocalContentColor.current
                        )
                        Spacer(Modifier.width(12.dp))
                        Text("Ingresando")
                    } else {
                        Text("Ingresar")
                    }
                }
            }
        }
    }
}

@Composable
private fun Pokebola() {
    val rojo = MaterialTheme.colorScheme.primary
    val blanco = MaterialTheme.colorScheme.surface
    val trazo = MaterialTheme.colorScheme.onSurface
    Canvas(modifier = Modifier.size(88.dp)) {
        val radio = size.minDimension / 2
        val grosor = radio * 0.12f
        drawArc(color = rojo, startAngle = 180f, sweepAngle = 180f, useCenter = true)
        drawArc(color = blanco, startAngle = 0f, sweepAngle = 180f, useCenter = true)
        drawCircle(color = trazo, style = Stroke(width = grosor))
        drawLine(trazo, Offset(0f, radio), Offset(radio * 2, radio), strokeWidth = grosor)
        drawCircle(color = blanco, radius = radio * 0.28f)
        drawCircle(color = trazo, radius = radio * 0.28f, style = Stroke(width = grosor))
    }
}
