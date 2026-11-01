package com.salazar.recetas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.salazar.recetas.data.model.RecipeModel
import com.salazar.recetas.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(
    id: Int,
    viewModel: RecipeViewModel,
    onBack: () -> Unit
) {
    val receta by viewModel.selected.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadRecipe(id)
    }

    val actual = receta
    if (actual == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = actual.image,
                contentDescription = actual.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(12.dp)
                    .size(44.dp)
                    .background(Color.Black.copy(alpha = 0.55f), CircleShape)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = actual.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${actual.cuisine} · ${actual.mealType.joinToString(", ")} · ${actual.difficulty}",
                style = MaterialTheme.typography.bodyLarge
            )
            DatosReceta(actual)
            SeccionLista(titulo = "Ingredientes", items = actual.ingredients, numerada = false)
            SeccionLista(titulo = "Preparacion", items = actual.instructions, numerada = true)
        }
    }
}

@Composable
private fun DatosReceta(receta: RecipeModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Dato("Tiempo", "${receta.prepTimeMinutes + receta.cookTimeMinutes} min", Modifier.weight(1f))
        Dato("Porciones", "${receta.servings}", Modifier.weight(1f))
        Dato("Calorias", "${receta.caloriesPerServing}", Modifier.weight(1f))
        Dato("Puntaje", "${receta.rating}", Modifier.weight(1f))
    }
}

@Composable
private fun Dato(titulo: String, valor: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = valor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = titulo, fontSize = 12.sp)
    }
}

@Composable
private fun SeccionLista(titulo: String, items: List<String>, numerada: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            items.forEachIndexed { indice, texto ->
                val marca = if (numerada) "${indice + 1}." else "•"
                Row {
                    Text(text = marca, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
                    Text(text = texto)
                }
            }
        }
    }
}
