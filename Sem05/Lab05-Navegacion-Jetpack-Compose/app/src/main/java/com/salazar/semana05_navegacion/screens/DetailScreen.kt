package com.salazar.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class DetailStudentInfo(
    val id: Int,
    val name: String,
    val career: String,
    val studentIdCode: String,
    val email: String,
    val faculty: String,
    val biography: String,
    val initials: String
)

fun getStudentDetails(itemId: Int): DetailStudentInfo {
    return when (itemId) {
        1 -> DetailStudentInfo(
            id = 1,
            name = "Johan Salazar Atencio",
            career = "Ingeniería de Sistemas",
            studentIdCode = "ID Estudiante: 2024-0001",
            email = "Correo Electrónico: johan.salazar@tecsup.edu.pe",
            faculty = "Facultad: Ingeniería y Tecnología",
            biography = "Estudiante destacado de la carrera de Ingeniería de Sistemas. Apasionado por el desarrollo de software móvil, arquitecturas limpias y tecnologías cloud en Android.",
            initials = "JS"
        )
        2 -> DetailStudentInfo(
            id = 2,
            name = "María García López",
            career = "Diseño y Desarrollo de Software",
            studentIdCode = "ID Estudiante: 2024-0002",
            email = "Correo Electrónico: maria.garcia@tecsup.edu.pe",
            faculty = "Facultad: Tecnología Digital",
            biography = "Especialista en experiencia de usuario (UX/UI) y desarrollo frontend para plataformas web y móviles.",
            initials = "MG"
        )
        3 -> DetailStudentInfo(
            id = 3,
            name = "Carlos Pérez Ruiz",
            career = "Redes y Comunicaciones",
            studentIdCode = "ID Estudiante: 2024-0003",
            email = "Correo Electrónico: carlos.perez@tecsup.edu.pe",
            faculty = "Facultad: Infraestructura IT",
            biography = "Enfocado en seguridad de redes, virtualización de servidores y administración de infraestructura de nube.",
            initials = "CP"
        )
        else -> DetailStudentInfo(
            id = itemId,
            name = "Johan Salazar Atencio",
            career = "Ingeniería de Sistemas",
            studentIdCode = "ID Estudiante: 2024-000$itemId",
            email = "Correo Electrónico: johan.salazar@tecsup.edu.pe",
            faculty = "Facultad: Ingeniería y Tecnología",
            biography = "Estudiante destacado de la carrera de Ingeniería de Sistemas. Apasionado por la innovación tecnológica y el desarrollo móvil con Jetpack Compose.",
            initials = "JS"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: Int) {
    val student = getStudentDetails(itemId)

    Scaffold(
        containerColor = Color(0xFFF8F5FA),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Expediente Académico",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3F2B68),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header superior con banner decorativo en degradado morado oscuro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF3F2B68),
                                Color(0xFF523D7F)
                            )
                        )
                    )
            )

            // Contenedor para la foto de perfil circular que sobrepasa el borde del banner
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = (-50).dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFFEDE7F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = student.initials,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF523D7F)
                        )
                    }
                }

                // Nombre y Carrera
                Column(
                    modifier = Modifier.offset(y = (-36).dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = student.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D1B4E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = student.career,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF6E6A7C)
                    )
                }

                Spacer(modifier = Modifier.height((-20).dp))

                // Tarjeta blanca con información en filas estilizadas
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        DetailInfoRow(
                            icon = Icons.Outlined.Badge,
                            text = student.studentIdCode
                        )

                        HorizontalDivider(color = Color(0xFFF0EBF8))

                        DetailInfoRow(
                            icon = Icons.Outlined.Email,
                            text = student.email
                        )

                        HorizontalDivider(color = Color(0xFFF0EBF8))

                        DetailInfoRow(
                            icon = Icons.Outlined.School,
                            text = student.faculty
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección Biografía
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Biografía",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF523D7F)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = student.biography,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4A4458),
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFEDE7F6)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF523D7F),
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2D1B4E)
        )
    }
}
