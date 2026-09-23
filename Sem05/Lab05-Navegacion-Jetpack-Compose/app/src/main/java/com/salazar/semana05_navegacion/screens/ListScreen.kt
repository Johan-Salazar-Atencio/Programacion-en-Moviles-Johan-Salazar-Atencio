package com.salazar.semana05_navegacion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ListScreen(navController: NavController) {
    val itemsList = (1..10).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
        text = "Lista de Elementos",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(itemsList) {itemId ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            //Navegar a la ruta "detail/X enviando el id seleccionado
                            navController.navigate("detail/$itemId")
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ){
                    Text(
                        text = "Elememto #$itemId",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
            }

        }
    }

}