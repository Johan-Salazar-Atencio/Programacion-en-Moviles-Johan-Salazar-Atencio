package com.salazar.semana05_navegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.semana05_navegacion.screens.DetailScreen
import com.salazar.semana05_navegacion.screens.HomeScreen
import com.salazar.semana05_navegacion.screens.ListScreen
import com.salazar.semana05_navegacion.screens.LoginScreen
import com.salazar.semana05_navegacion.screens.ProfileScreen

@Composable
fun AppNavigation() {
    //para recordar el controlador de navegacion
    val navController = rememberNavController()

    //Definir el NavHost con la ruta de inicio (LoginScreen)
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        //Ruta0: Login
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        //Ruta1: Inicio
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        //Ruta2 Lista de elementos
        composable(route = Screen.List.route) {
            ListScreen(navController = navController)
        }

        //Ruta3 Perfil
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        //Ruta4 Detalle con parametro "itemId"
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(name = "itemId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            //Extraeremos el numero "itemId" de la ruta
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0

            //Le pasamos el itemId recibido a la pantalla de detalle
            DetailScreen(navController = navController, itemId = itemId)
        }
    }
}
