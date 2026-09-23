package com.salazar.tecsupfit.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    onNavigateToTab: (Int) -> Unit = {}
) {
    val navItems = listOf("Inicio", "Reservas", "Rutinas", "Perfil")
    val navIcons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.AutoMirrored.Filled.List,
        Icons.Default.Person
    )

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF00695C), Color(0xFF004D40))
    )

    Scaffold(
        containerColor = Color(0xFFF4F7F6),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(navIcons[index], contentDescription = item) },
                        label = { Text(item, fontSize = 12.sp, fontWeight = if (index == 1) FontWeight.Bold else FontWeight.Normal) },
                        selected = index == 1,
                        onClick = { onNavigateToTab(index) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF00695C),
                            selectedTextColor = Color(0xFF00695C),
                            indicatorColor = Color(0xFFE8F5E9),
                            unselectedIconColor = Color(0xFF8C9A96),
                            unselectedTextColor = Color(0xFF8C9A96)
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
        ) {
            // Cabecera superior con degradado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradientBrush)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Mis reservas",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Tus clases programadas y pasadas",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.White.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EventAvailable,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Lista de reservas
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                if (reservations.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aún no tienes reservas activas.",
                            color = Color(0xFF6C7A77),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(reservations) { reservation ->
                            // Transición animada de color según estado de la reserva
                            val animatedBadgeBg by animateColorAsState(
                                targetValue = if (reservation.isConfirmed) Color(0xFFE8F5E9) else Color(0xFFF0F4F3),
                                animationSpec = tween(durationMillis = 300),
                                label = "badgeBg"
                            )
                            val animatedBadgeTextColor by animateColorAsState(
                                targetValue = if (reservation.isConfirmed) Color(0xFF00695C) else Color(0xFF6C7A77),
                                animationSpec = tween(durationMillis = 300),
                                label = "badgeText"
                            )
                            val animatedBarColor by animateColorAsState(
                                targetValue = if (reservation.isConfirmed) Color(0xFF00695C) else Color(0xFFB0BEC5),
                                animationSpec = tween(durationMillis = 300),
                                label = "barColor"
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                border = BorderStroke(1.dp, Color(0xFFE0ECE8)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min)
                                ) {
                                    // Barra lateral animada
                                    Box(
                                        modifier = Modifier
                                            .width(8.dp)
                                            .fillMaxHeight()
                                            .background(animatedBarColor)
                                    )

                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = reservation.className,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 17.sp,
                                                color = Color(0xFF1A2523)
                                            )

                                            Surface(
                                                shape = RoundedCornerShape(12.dp),
                                                color = animatedBadgeBg
                                            ) {
                                                Text(
                                                    text = reservation.status,
                                                    color = animatedBadgeTextColor,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AccessTime,
                                                contentDescription = null,
                                                tint = Color(0xFF6C7A77),
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = reservation.time,
                                                fontSize = 13.sp,
                                                color = Color(0xFF6C7A77)
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
}
