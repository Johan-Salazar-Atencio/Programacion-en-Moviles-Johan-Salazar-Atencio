package com.salazar.tecsupfit.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Exercise(
    val id: Int,
    val name: String,
    val reps: String
)

data class Routine(
    val id: Int,
    val title: String,
    val category: String,
    val duration: String,
    val difficulty: String,
    val exercises: List<Exercise>
)

val sampleRoutines = listOf(
    Routine(
        id = 1,
        title = "Rutina de Fuerza Total",
        category = "Fuerza",
        duration = "45 min",
        difficulty = "Avanzado",
        exercises = listOf(
            Exercise(1, "Sentadillas con barra", "4 series x 10 reps"),
            Exercise(2, "Press de banca", "4 series x 8 reps"),
            Exercise(3, "Peso muerto", "3 series x 8 reps"),
            Exercise(4, "Remo con mancuerna", "3 series x 12 reps")
        )
    ),
    Routine(
        id = 2,
        title = "Cardio HIIT Quemagrasa",
        category = "HIIT",
        duration = "30 min",
        difficulty = "Intermedio",
        exercises = listOf(
            Exercise(5, "Burpees", "4 series x 45 seg"),
            Exercise(6, "Mountain Climbers", "4 series x 45 seg"),
            Exercise(7, "Jumping Jacks", "4 series x 60 seg"),
            Exercise(8, "Zancadas con salto", "4 series x 30 seg")
        )
    ),
    Routine(
        id = 3,
        title = "Yoga & Estiramiento Postural",
        category = "Yoga",
        duration = "40 min",
        difficulty = "Principiante",
        exercises = listOf(
            Exercise(9, "Saludo al Sol", "3 repeticiones completas"),
            Exercise(10, "Postura del Guerrero I y II", "2 min cada lado"),
            Exercise(11, "Postura del Perro Mirando Abajo", "3 min sostenidos"),
            Exercise(12, "Estiramiento de Isquiotibiales", "2 min sostenidos")
        )
    )
)

@Composable
fun RutinasScreen(
    routines: List<Routine> = sampleRoutines,
    onNavigateToTab: (Int) -> Unit = {}
) {
    val navItems = listOf("Inicio", "Reservas", "Rutinas", "Perfil")
    val navIcons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.AutoMirrored.Filled.List,
        Icons.Default.Person
    )

    // Estado para guardar qué ejercicios están marcados como completados (key = Pair(routineId, exerciseId))
    val checkedState = remember { mutableStateMapOf<Pair<Int, Int>, Boolean>() }

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
                        label = { Text(item, fontSize = 12.sp, fontWeight = if (index == 2) FontWeight.Bold else FontWeight.Normal) },
                        selected = index == 2,
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
            // Cabecera en degradado
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
                            text = "Rutinas de entrenamiento",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Sigue tus ejercicios y marca tus progresos",
                            fontSize = 13.sp,
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
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Lista de rutinas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(routines) { routine ->
                    RoutineCard(
                        routine = routine,
                        checkedState = checkedState
                    )
                }
            }
        }
    }
}

@Composable
fun RoutineCard(
    routine: Routine,
    checkedState: androidx.compose.runtime.snapshots.SnapshotStateMap<Pair<Int, Int>, Boolean>
) {
    var isExpanded by remember { mutableStateOf(true) }

    val completedCount = routine.exercises.count { exercise ->
        checkedState[Pair(routine.id, exercise.id)] == true
    }

    val difficultyColor = when (routine.difficulty) {
        "Principiante" -> Color(0xFF00695C)
        "Intermedio" -> Color(0xFFE65100)
        else -> Color(0xFFC62828)
    }

    val difficultyBg = when (routine.difficulty) {
        "Principiante" -> Color(0xFFE8F5E9)
        "Intermedio" -> Color(0xFFFFF3E0)
        else -> Color(0xFFFFEBEE)
    }

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
            // Encabezado de la rutina
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = routine.category,
                                color = Color(0xFF00695C),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = difficultyBg
                        ) {
                            Text(
                                text = routine.difficulty,
                                color = difficultyColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = routine.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFF1A2523)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = Color(0xFF6C7A77),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = routine.duration,
                            fontSize = 13.sp,
                            color = Color(0xFF6C7A77)
                        )
                        Text(
                            text = "  ·  ",
                            fontSize = 13.sp,
                            color = Color(0xFF6C7A77)
                        )
                        Text(
                            text = "$completedCount/${routine.exercises.size} completados",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (completedCount == routine.exercises.size) Color(0xFF00695C) else Color(0xFF6C7A77)
                        )
                    }
                }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Plegar" else "Desplegar",
                        tint = Color(0xFF00695C)
                    )
                }
            }

            // Lista de ejercicios interactivos
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    HorizontalDivider(color = Color(0xFFE0ECE8), modifier = Modifier.padding(bottom = 10.dp))

                    routine.exercises.forEach { exercise ->
                        val key = Pair(routine.id, exercise.id)
                        val isChecked = checkedState[key] ?: false

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { checkedState[key] = !isChecked }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checkedState[key] = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF00695C),
                                    uncheckedColor = Color(0xFF8C9A96)
                                )
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = exercise.name,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = if (isChecked) Color(0xFF8C9A96) else Color(0xFF1A2523),
                                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
                                )
                                Text(
                                    text = exercise.reps,
                                    fontSize = 12.sp,
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
