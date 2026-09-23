package com.salazar.tecsupfit.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    totalClasses: Int = 0,
    completedClasses: Int = 0,
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

    // Estados para los campos de la Calculadora de IMC
    val weightInput = remember { mutableStateOf("") }
    val heightInput = remember { mutableStateOf("") }
    val bmiResult = remember { mutableStateOf<Double?>(null) }
    val bmiCategory = remember { mutableStateOf("") }
    val bmiRecommendation = remember { mutableStateOf("") }

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
                        label = { Text(item, fontSize = 12.sp, fontWeight = if (index == 3) FontWeight.Bold else FontWeight.Normal) },
                        selected = index == 3,
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
                .verticalScroll(rememberScrollState())
        ) {
            // Contenedor de cabecera con degradado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = gradientBrush,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Mi Perfil",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Avatar con borde y fondo suaves
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color.White, shape = CircleShape)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFE8F5E9), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "JS",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00695C)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Johan Salazar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Plan Premium",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Sección de estadísticas
                Text(
                    text = "Resumen de actividad",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A2523),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Tarjeta Total de Clases
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        border = BorderStroke(1.dp, Color(0xFFE0ECE8)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = Color(0xFF00695C),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$totalClasses",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00695C)
                            )
                            Text(
                                text = "Clases reservadas",
                                fontSize = 11.sp,
                                color = Color(0xFF6C7A77)
                            )
                        }
                    }

                    // Tarjeta Rachas/Completadas
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        border = BorderStroke(1.dp, Color(0xFFE0ECE8)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = Color(0xFF00695C),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$completedClasses",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00695C)
                            )
                            Text(
                                text = "Rachas activas",
                                fontSize = 11.sp,
                                color = Color(0xFF6C7A77)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Módulo de Calculadora de IMC
                Text(
                    text = "Calculadora de IMC",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A2523),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0ECE8)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = weightInput.value,
                                onValueChange = { weightInput.value = it },
                                label = { Text("Peso (kg)") },
                                placeholder = { Text("Ej: 70") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.MonitorWeight,
                                        contentDescription = null,
                                        tint = Color(0xFF00695C)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF00695C),
                                    unfocusedBorderColor = Color(0xFFE0ECE8)
                                )
                            )

                            OutlinedTextField(
                                value = heightInput.value,
                                onValueChange = { heightInput.value = it },
                                label = { Text("Altura (cm)") },
                                placeholder = { Text("Ej: 175") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Height,
                                        contentDescription = null,
                                        tint = Color(0xFF00695C)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF00695C),
                                    unfocusedBorderColor = Color(0xFFE0ECE8)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val weight = weightInput.value.toDoubleOrNull()
                                val heightCm = heightInput.value.toDoubleOrNull()

                                if (weight != null && heightCm != null && heightCm > 0) {
                                    val heightM = heightCm / 100.0
                                    val imc = weight / (heightM * heightM)
                                    bmiResult.value = imc

                                    when {
                                        imc < 18.5 -> {
                                            bmiCategory.value = "Bajo peso"
                                            bmiRecommendation.value = "Recomendado: Fuerza Total o Rutina de Musculación"
                                        }
                                        imc in 18.5..24.99 -> {
                                            bmiCategory.value = "Normal"
                                            bmiRecommendation.value = "Recomendado: Cross Training o HIIT"
                                        }
                                        imc in 25.0..29.99 -> {
                                            bmiCategory.value = "Sobrepeso"
                                            bmiRecommendation.value = "Recomendado: Yoga Funcional, Spinning o Pilates"
                                        }
                                        else -> {
                                            bmiCategory.value = "Obesidad"
                                            bmiRecommendation.value = "Recomendado: Yoga Suave o Ejercicio Cardiovascular de bajo impacto"
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00695C),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Calcular IMC",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Resultado del IMC y Tarjeta con recomendación en degradado
                bmiResult.value?.let { imc ->
                    Spacer(modifier = Modifier.height(16.dp))

                    val formattedImc = String.format(Locale.US, "%.1f", imc)

                    val categoryBgColor = when (bmiCategory.value) {
                        "Normal" -> Color(0xFFE8F5E9)
                        "Bajo peso" -> Color(0xFFE3F2FD)
                        "Sobrepeso" -> Color(0xFFFFF3E0)
                        else -> Color(0xFFFFEBEE)
                    }

                    val categoryTextColor = when (bmiCategory.value) {
                        "Normal" -> Color(0xFF00695C)
                        "Bajo peso" -> Color(0xFF1565C0)
                        "Sobrepeso" -> Color(0xFFE65100)
                        else -> Color(0xFFC62828)
                    }

                    // Tarjeta de resultado e información del IMC
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        border = BorderStroke(1.dp, Color(0xFFE0ECE8)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Tu Índice de Masa Corporal",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6C7A77)
                                )
                                Text(
                                    text = "$formattedImc kg/m²",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A2523)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = categoryBgColor
                            ) {
                                Text(
                                    text = bmiCategory.value,
                                    color = categoryTextColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tarjeta con recomendación en degradado
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        border = BorderStroke(1.dp, Color(0xFFE0ECE8))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(gradientBrush)
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD54F),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = "Recomendación personalizada:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White.copy(alpha = 0.85f)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = bmiRecommendation.value,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
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
