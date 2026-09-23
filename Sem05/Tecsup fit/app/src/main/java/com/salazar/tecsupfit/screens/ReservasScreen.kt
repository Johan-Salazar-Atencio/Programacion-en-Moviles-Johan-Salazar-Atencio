package com.salazar.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.tecsupfit.ui.theme.TecsupFitTheme

data class Reservation(
    val id: Int,
    val className: String,
    val time: String,
    val status: String,
    val isConfirmed: Boolean
)

val sampleReservations = listOf(
    Reservation(1, "Cross Training", "Hoy, 6:00 pm", "Confirmada", true),
    Reservation(2, "Yoga funcional", "Ayer, 7:00 am", "Completada", false)
)

@Composable
fun ReservasScreen(
    reservations: List<Reservation> = sampleReservations,
    onNavigateToHome: () -> Unit = {}
) {
    val navItems = listOf("Inicio", "Reservas", "Rutinas", "Perfil")
    val navIcons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.Default.List,
        Icons.Default.Person
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(navIcons[index], contentDescription = item) },
                        label = { Text(item, fontSize = 12.sp) },
                        selected = index == 1, // 1 = Reservas
                        onClick = {
                            if (index == 0) {
                                onNavigateToHome()
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF00695C),
                            selectedTextColor = Color(0xFF00695C),
                            indicatorColor = Color.Transparent,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(
                text = "Mis reservas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (reservations.isEmpty()) {
                Text(
                    text = "Aún no tienes reservas activas.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(reservations) { reservation ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF2F2F2)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                            ) {
                                if (reservation.isConfirmed) {
                                    Box(
                                        modifier = Modifier
                                            .width(6.dp)
                                            .fillMaxHeight()
                                            .background(Color(0xFF00695C))
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = reservation.className,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = reservation.time,
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (reservation.isConfirmed) Color(0xFFE8F5E9) else Color(0xFFE0E0E0)
                                    ) {
                                        Text(
                                            text = reservation.status,
                                            color = if (reservation.isConfirmed) Color(0xFF00695C) else Color.Gray,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
