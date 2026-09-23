package com.salazar.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.semana05_navegacion.navigation.Screen

data class StudentItem(
    val id: Int,
    val name: String,
    val career: String,
    val initials: String
)

val sampleStudents = listOf(
    StudentItem(1, "Johan Salazar Atencio", "Ingeniería de Sistemas", "JS"),
    StudentItem(2, "María García López", "Diseño y Desarrollo de Software", "MG"),
    StudentItem(3, "Carlos Pérez Ruiz", "Redes y Comunicaciones", "CP"),
    StudentItem(4, "Ana Martínez Torres", "Big Data y Analítica", "AM"),
    StudentItem(5, "Luis Rodríguez Silva", "Desarrollo de Software", "LR"),
    StudentItem(6, "Elena Gómez Castro", "Ciberseguridad", "EG"),
    StudentItem(7, "Diego Fernández Vega", "Sistemas de Información", "DF"),
    StudentItem(8, "Sofía López Morales", "Ingeniería de Software", "SL")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFF8F5FA),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Directorio de Alumnos",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D1B4E)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF2D1B4E)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F5FA)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleStudents) { student ->
                StudentCardItem(
                    student = student,
                    onClick = {
                        navController.navigate(Screen.Detail.createRoute(student.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun StudentCardItem(
    student: StudentItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto de perfil circular (Avatar)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEDE7F6)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = student.initials,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF523D7F),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D1B4E)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = student.career,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6E6A7C)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver detalles",
                tint = Color(0xFFB3A8CA)
            )
        }
    }
}
