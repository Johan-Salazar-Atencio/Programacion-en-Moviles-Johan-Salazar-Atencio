package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun PerfilScreen(
    onNavegar: (Int) -> Unit = {}
) {
    var seleccionado by remember { mutableStateOf(3) }

    Scaffold(
        bottomBar = { BarraInferior(onNavegar = onNavegar, seleccionado = seleccionado) }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Mi Perfil",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Foto de perfil",
                    tint = VerdeBodega,
                    modifier = Modifier
                        .size(88.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .padding(4.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ItemPerfil("Datos personales", Icons.Outlined.Person, onClick = {})
                ItemPerfil("Direcciones", Icons.Outlined.LocationOn, onClick = {})
                ItemPerfil("Métodos de pago", Icons.Outlined.CreditCard, onClick = {})
                ItemPerfil("Configuración", Icons.Outlined.Settings, onClick = {})
            }
        }
    }
}

@Composable
private fun ItemPerfil(
    texto: String,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = VerdeBodega,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = texto,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BarraInferior(onNavegar: (Int) -> Unit, seleccionado: Int) {
    val items = listOf(
        Triple("Inicio", Icons.Default.Home, 0),
        Triple("Categorías", Icons.Default.Category, 1),
        Triple("Pedidos", Icons.Default.ReceiptLong, 2),
        Triple("Perfil", Icons.Default.Person, 3)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { (etiqueta, icono, indice) ->
            NavigationBarItem(
                selected = seleccionado == indice,
                onClick = {
                    onNavegar(indice)
                },
                icon = { Icon(icono, contentDescription = etiqueta) },
                label = { Text(etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeBodega,
                    selectedTextColor = VerdeBodega,
                    indicatorColor = VerdeBodega.copy(alpha = 0.15f)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PerfilPreview() {
    BodegaTheme {
        PerfilScreen()
    }
}
