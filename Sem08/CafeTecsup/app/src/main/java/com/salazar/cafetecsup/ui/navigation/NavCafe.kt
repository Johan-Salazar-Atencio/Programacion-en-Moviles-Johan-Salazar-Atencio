package com.salazar.cafetecsup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.cafetecsup.ui.cliente.DetalleProductoScreen
import com.salazar.cafetecsup.ui.cliente.LoginScreen
import com.salazar.cafetecsup.ui.cliente.MenuScreen
import com.salazar.cafetecsup.viewmodel.MenuViewModel
import com.salazar.cafetecsup.viewmodel.SesionViewModel

@Composable
fun NavCafe(viewModel: SesionViewModel, menuViewModel: MenuViewModel) {
    val navController = rememberNavController()
    val perfilActivo by viewModel.perfilActivo.collectAsState()

    val startDestination = when (perfilActivo) {
        "CLIENTE" -> "menu_cliente"
        "ADMIN" -> "panel_admin"
        else -> "login"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { rol ->
                    val ruta = if (rol == "ADMIN") "panel_admin" else "menu_cliente"
                    navController.navigate(ruta) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("menu_cliente") {
            MenuScreen(
                vm = menuViewModel,
                onProducto = { productoId ->
                    navController.navigate("detalle/$productoId")
                }
            )
        }

        composable(
            route = "detalle/{productoId}",
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            DetalleProductoScreen(
                productoId = productoId,
                vm = menuViewModel,
                onVolver = { navController.popBackStack() }
            )
        }

        composable("panel_admin") {
            androidx.compose.material3.Text("Bienvenido al Panel de Administración")
        }
    }
}