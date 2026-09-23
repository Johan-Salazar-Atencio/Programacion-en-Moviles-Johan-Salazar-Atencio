package com.salazar.semana05_navegacion.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.semana05_navegacion.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Pantalla de Inicio",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Boton para ir a la pantalla de lista
        Button(
            onClick = {navController.navigate(Screen.List.route)},
            modifier = Modifier.fillMaxWidth(0.8f)
        ){
            Text(text = "Ver Lista de Elementos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        //Boton para ir a la pantalla Perfil
        Button(
            onClick = {navController.navigate(Screen.Profile.route) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ){
            Text(text = "Ver Lista de Elementos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        //Botton para ir a la pantalla de Perfil
        Button(
            onClick = {navController.navigate(Screen.List.route) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ){
            Text(text = "Ver Lista de Elementos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        //Boton para ir a la Pantalla de Perfil

        Button(
            onClick = {navController.navigate(Screen.Profile.route) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Ir al Perfil")
        }
    }
}
