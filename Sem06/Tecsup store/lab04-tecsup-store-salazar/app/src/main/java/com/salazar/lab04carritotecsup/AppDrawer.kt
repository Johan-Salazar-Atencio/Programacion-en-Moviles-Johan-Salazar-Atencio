package com.salazar.lab04carritotecsup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class DestinoDrawer(val titulo: String, val icono: ImageVector) {
    INICIO("Inicio", Icons.Default.Home),
    MIS_PEDIDOS("Mis pedidos", Icons.Default.ShoppingCart),
    FAVORITOS("Favoritos", Icons.Default.Favorite),
    PERFIL("Perfil", Icons.Default.Person),
    CERRAR_SESION("Cerrar sesión", Icons.AutoMirrored.Filled.ExitToApp)
}

@Composable
fun AppDrawer(
    destinoSeleccionado: DestinoDrawer,
    onDestinoSeleccionado: (DestinoDrawer) -> Unit,
    modifier: Modifier = Modifier,
    cantidadFavoritos: Int = 0
) {
    ModalDrawerSheet(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar circular con iniciales
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JS",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Nombre y correo del usuario
                Column {
                    Text(
                        text = "Johan Salazar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "johan@tecsup.edu.pe",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(12.dp))

        //Opciones
        DestinoDrawer.entries.forEach { destino ->
            NavigationDrawerItem(
                label = { Text(destino.titulo) },
                icon = { Icon(imageVector = destino.icono, contentDescription = destino.titulo) },
                badge = if ((destino == DestinoDrawer.FAVORITOS) && (cantidadFavoritos > 0)) {
                    { Badge { Text(cantidadFavoritos.toString()) } }
                } else null,
                selected = destino == destinoSeleccionado,
                onClick = { onDestinoSeleccionado(destino) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}