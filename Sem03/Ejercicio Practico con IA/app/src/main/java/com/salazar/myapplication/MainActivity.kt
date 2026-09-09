package com.salazar.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.salazar.myapplication.ui.theme.MyApplicationTheme
import java.util.Locale
import kotlin.math.roundToInt

// ==========================================
// 1. MODELO DE DATOS Y ENUM (POO)
// ==========================================

/**
 * Entidad Curso con su peso y nota.
 * Incluye un método de negocio para calcular su propio aporte.
 */
data class Curso(
    val nombre: String,
    val peso: Float,
    var nota: Float = 0f
) {
    fun calcularAporte(): Float = nota * peso
}

/**
 * Categoria visual con colores y títulos calculados mediante expresión 'when'.
 */
enum class EstadoNota(
    val titulo: String,
    val color: Color
) {
    EXCELENTE("EXCELENTE", Color(0xFF2E7D32)),
    APROBADO("APROBADO", Color(0xFF4CAF50)),
    RECUPERACION("EN RECUPERACIÓN", Color(0xFFFF9800)),
    DESAPROBADO("DESAPROBADO", Color(0xFFF44336));

    companion object {
        fun obtenerEstado(notaFinal: Float): EstadoNota = when {
            notaFinal >= 17f -> EXCELENTE
            notaFinal >= 13f -> APROBADO
            notaFinal >= 10f -> RECUPERACION
            else -> DESAPROBADO
        }
    }
}

// ==========================================
// 2. VIEWMODEL (LÓGICA DE NEGOCIO)
// ==========================================

/**
 * Gestor del estado de la aplicación. Maneja cursos, switches, checkboxes y promedios.
 */
class CalculadoraNotasViewModel : ViewModel() {

    val cursos = mutableStateListOf(
        Curso("Evaluación Continua 1", 0.20f),
        Curso("Evaluación Continua 2", 0.20f),
        Curso("Evaluación Continua 3", 0.30f),
        Curso("Examen Final", 0.30f)
    )

    var redondearNota by mutableStateOf(false)
        private set

    var notasConfirmadas by mutableStateOf(false)
        private set

    var promedioCalculado by mutableStateOf<Float?>(null)
        private set

    fun actualizarNota(index: Int, nuevaNota: Float) {
        cursos[index] = cursos[index].copy(nota = nuevaNota)
    }

    fun onRedondearChanged(nuevoValor: Boolean) {
        redondearNota = nuevoValor
    }

    fun onConfirmarChanged(nuevoValor: Boolean) {
        notasConfirmadas = nuevoValor
        if (!nuevoValor) promedioCalculado = null
    }

    fun calcularPromedio() {
        if (!notasConfirmadas) return
        val sumaAportes = cursos.sumOf { it.calcularAporte().toDouble() }.toFloat()
        promedioCalculado = if (redondearNota) sumaAportes.roundToInt().toFloat() else sumaAportes
    }

    /**
     * Reto 3: Limpia las notas a 0, apaga Switch, desmarca Checkbox y oculta la tarjeta.
     */
    fun limpiarCampos() {
        for (i in cursos.indices) {
            cursos[i] = cursos[i].copy(nota = 0f)
        }
        redondearNota = false
        notasConfirmadas = false
        promedioCalculado = null
    }
}

// ==========================================
// 3. ACTIVIDAD Y PANTALLA PRINCIPAL
// ==========================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistroNotasScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasScreen(
    // Instanciado con remember para total compatibilidad sin dependencias externas
    viewModel: CalculadoraNotasViewModel = remember { CalculadoraNotasViewModel() }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Notas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = { PieDePagina() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Ingreso de Calificaciones",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Sliders para los 4 Cursos
            viewModel.cursos.forEachIndexed { index, curso ->
                ItemCursoSlider(
                    curso = curso,
                    onNotaChange = { nuevaNota -> viewModel.actualizarNota(index, nuevaNota) }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            // Switch Redondeo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Redondear Nota Final", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = viewModel.redondearNota,
                    onCheckedChange = { viewModel.onRedondearChanged(it) }
                )
            }

            // Checkbox Confirmación
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.notasConfirmadas,
                    onCheckedChange = { viewModel.onConfirmarChanged(it) }
                )
                Text(
                    text = "Confirmo que las notas ingresadas son correctas",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Botones LIMPIAR y CALCULAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.limpiarCampos() },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Limpiar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("LIMPIAR")
                }

                Button(
                    onClick = { viewModel.calcularPromedio() },
                    enabled = viewModel.notasConfirmadas,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("CALCULAR")
                }
            }

            // Tarjeta de Resultados Animada
            AnimatedVisibility(visible = viewModel.promedioCalculado != null) {
                viewModel.promedioCalculado?.let { promedio ->
                    TarjetaResultados(
                        promedio = promedio,
                        cursos = viewModel.cursos,
                        esRedondeado = viewModel.redondearNota
                    )
                }
            }
        }
    }
}

// ==========================================
// 4. COMPONENTES VISUALES
// ==========================================

/**
 * Reto 1: Slider de 0 a 20 con badge de color (rojo < 13, verde >= 13) usando 'if' como expresión.
 */
@Composable
fun ItemCursoSlider(
    curso: Curso,
    onNotaChange: (Float) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${curso.nombre} (${(curso.peso * 100).toInt()}%)",
                    fontWeight = FontWeight.SemiBold
                )

                // Reto 1: Semáforo del Badge con 'if' como expresión
                val colorBadge = if (curso.nota < 13f) Color(0xFFE53935) else Color(0xFF4CAF50)

                Box(
                    modifier = Modifier
                        .background(colorBadge, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = String.format(Locale.US, "%.1f", curso.nota),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Slider(
                value = curso.nota,
                onValueChange = onNotaChange,
                valueRange = 0f..20f,
                steps = 19
            )
        }
    }
}

/**
 * Reto 2: Tarjeta con categoría visual e íconos + desglose por curso (Nota x Peso = Aporte).
 */
@Composable
fun TarjetaResultados(
    promedio: Float,
    cursos: List<Curso>,
    esRedondeado: Boolean
) {
    val estado = EstadoNota.obtenerEstado(promedio)

    Card(
        colors = CardDefaults.cardColors(containerColor = estado.color.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (promedio >= 10f) Icons.Default.CheckCircle else Icons.Default.Warning,
                contentDescription = null,
                tint = estado.color,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = estado.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = estado.color
            )

            val textoPromedio = if (esRedondeado) {
                "${promedio.toInt()}"
            } else {
                String.format(Locale.US, "%.2f", promedio)
            }

            Text(
                text = "Promedio Final: $textoPromedio",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = estado.color
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                text = "Desglose por Curso:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Reto 2: Formato -> Curso: Nota x Peso = Aporte
            cursos.forEach { c ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${c.nombre}:",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.1f", c.nota)} × ${c.peso} = ${String.format(Locale.US, "%.2f", c.calcularAporte())}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Pie de página requerido.
 */
@Composable
fun PieDePagina() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Desarrollado por: Johan Salazar Atencio",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4
)
@Composable
fun RegistroNotasPreview() {
    MyApplicationTheme {
        RegistroNotasScreen()
    }
}