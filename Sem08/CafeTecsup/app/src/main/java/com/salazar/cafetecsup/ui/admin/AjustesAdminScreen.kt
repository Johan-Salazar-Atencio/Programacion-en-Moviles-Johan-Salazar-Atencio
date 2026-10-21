package com.salazar.cafetecsup.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.viewmodel.SesionViewModel

@Composable
fun AjustesAdminScreen(
    viewModel: SesionViewModel,
    onNavegarPanel: () -> Unit,
    onNavegarPedidos: () -> Unit,
    onNavegarMenu: () -> Unit,
    onCambiarACliente: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    val modoOscuro by viewModel.modoOscuro.collectAsState()
    val notificaciones by viewModel.notificaciones.collectAsState()
    val recibirPedidos by viewModel.recibirPedidos.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = colorScheme.surface, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarPanel,
                    icon = { Text("📊", fontSize = 18.sp) },
                    label = { Text("Panel", color = colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarPedidos,
                    icon = { Text("📋", fontSize = 18.sp) },
                    label = { Text("Pedidos", color = colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavegarMenu,
                    icon = { Text("☕", fontSize = 18.sp) },
                    label = { Text("Menú", color = colorScheme.onSurfaceVariant) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Text("⚙️", fontSize = 18.sp) },
                    label = { Text("Ajustes", color = colorScheme.primary, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = colorScheme.primary.copy(alpha = 0.15f))
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text("Ajustes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = colorScheme.onBackground)

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Administrador", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = colorScheme.onSurface)
                        Text("admin@cafe.test", fontSize = 13.sp, color = colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(6.dp))

                        Surface(
                            color = colorScheme.primary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Perfil activo",
                                color = colorScheme.primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.cambiarACliente()
                        onCambiarACliente()
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Cambiar de perfil", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = colorScheme.onPrimary)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Modo práctica: ir a vista Estudiante", fontSize = 12.sp, color = colorScheme.onPrimary.copy(alpha = 0.8f))
                    }
                    Text("›", fontSize = 24.sp, color = colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Preferencias", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colorScheme.onBackground)

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Modo oscuro", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = colorScheme.onSurface)
                            Text("Cambia el tema de la app", fontSize = 12.sp, color = colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = modoOscuro,
                            onCheckedChange = { viewModel.cambiarModoOscuro(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorScheme.onPrimary,
                                checkedTrackColor = colorScheme.primary
                            )
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = colorScheme.outline)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Notificaciones", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = colorScheme.onSurface)
                            Text("Avisar nuevos pedidos", fontSize = 12.sp, color = colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = notificaciones,
                            onCheckedChange = { viewModel.cambiarNotificaciones(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorScheme.onPrimary,
                                checkedTrackColor = colorScheme.primary
                            )
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = colorScheme.outline)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Recibir pedidos", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = colorScheme.onSurface)
                            Text("Abrir o cerrar la cafetería", fontSize = 12.sp, color = colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = recibirPedidos,
                            onCheckedChange = { viewModel.cambiarRecibirPedidos(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorScheme.onPrimary,
                                checkedTrackColor = colorScheme.primary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Se guardan en el dispositivo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = colorScheme.primary)
                    Text("con DataStore", fontSize = 12.sp, color = colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    viewModel.cerrarSesion()
                    onCerrarSesion()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colorScheme.error)
            ) {
                Text("Cerrar sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = colorScheme.error)
            }
        }
    }
}
