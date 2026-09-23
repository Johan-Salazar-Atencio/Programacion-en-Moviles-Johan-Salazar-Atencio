package com.salazar.semana05_navegacion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    // Lista de 8 elementos
    val items = (1..8).map { "Elemento número $it" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(items.size) { index ->
                ListItem(
                    headlineContent = { Text(text = items[index]) },
                    supportingContent = { Text(text = "Toca para ver el detalle") },
                    modifier = Modifier.clickable {
                        // Navega enviando el id del elemento (1, 2, 3...)
                        navController.navigate("detail/${index + 1}")
                    }
                )
                HorizontalDivider() // Línea separadora entre elementos
            }
        }
    }
}