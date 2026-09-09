package com.salazar.registrodenotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.registrodenotas.ui.theme.RegistroDeNotasTheme
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroDeNotasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistroNotasScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistroNotasScreen(modifier: Modifier = Modifier) {

    // Estados de notas
    var nota1 by remember { mutableFloatStateOf(0f) }
    var nota2 by remember { mutableFloatStateOf(0f) }
    var nota3 by remember { mutableFloatStateOf(0f) }
    var nota4 by remember { mutableFloatStateOf(0f) }

    // Estados de confirmación, switch y cálculo
    var redondear by remember { mutableStateOf(false) }
    var confirmacion by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFEDE7F6), Color(0xFFF3E5F5))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5E4B8B))
                    .padding(vertical = 20.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Registro de Notas",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Notas del ciclo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Desliza para asignar cada nota (0 a 20)",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ItemCursoSlider("Fundamentos de Programación", "(20%)", nota1) { nota1 = it }
                ItemCursoSlider("Programación Orientada a Objetos", "(25%)", nota2) { nota2 = it }
                ItemCursoSlider("Programación en Móviles", "(30%)", nota3) { nota3 = it }
                ItemCursoSlider("Base de Datos", "(25%)", nota4) { nota4 = it }

                Spacer(modifier = Modifier.height(8.dp))

                // Switch: Para Redondear promedio
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Redondear promedio final", fontSize = 14.sp)
                    Switch(
                        checked = redondear,
                        onCheckedChange = { redondear = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF5E4B8B)
                        )
                    )
                }

                // Checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = confirmacion,
                        onCheckedChange = { confirmacion = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF5E4B8B)
                        )
                    )
                    Text(text = "Confirmo que las notas son correctas", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Boton de Calcular Promedio
                Button(
                    onClick = { calculado = true },
                    enabled = confirmacion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5E4B8B),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "CALCULAR PROMEDIO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                // Resultados
                if (calculado) {
                    Spacer(modifier = Modifier.height(20.dp))

                    val promedioSinRedondear = (nota1 * 0.20f) + (nota2 * 0.25f) + (nota3 * 0.30f) + (nota4 * 0.25f)
                    val promedioFinal = if (redondear) promedioSinRedondear.roundToInt().toFloat() else promedioSinRedondear
                    val aprobado = promedioFinal >= 13.0f

                    val colorEstado = if (aprobado) Color(0xFF2E7D32) else Color(0xFFC62828)
                    val textoEstado = if (aprobado) "Aprobado" else "Desaprobado"
                    val iconoEstado = if (aprobado) Icons.Default.Check else Icons.Default.Close

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "RESULTADOS DEL CICLO",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (redondear) "${promedioFinal.toInt()}" else String.format("%.2f", promedioFinal),
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                color = Color(0xFF5E4B8B)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = colorEstado.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = iconoEstado,
                                        contentDescription = null,
                                        tint = colorEstado,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = textoEstado,
                                        color = colorEstado,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
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

@Composable
fun ItemCursoSlider(
    titulo: String,
    pesoText: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF2C2C2C)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = pesoText,
                    fontSize = 12.sp,
                    color = Color(0xFF7E57C2)
                )
            }
            Surface(
                color = Color(0xFFEDE7F6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${value.toInt()}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5E4B8B)
                )
            }
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            steps = 19,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF5E4B8B),
                activeTrackColor = Color(0xFF5E4B8B),
                inactiveTrackColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroNotasPreview() {
    RegistroDeNotasTheme {
        RegistroNotasScreen()
    }
}