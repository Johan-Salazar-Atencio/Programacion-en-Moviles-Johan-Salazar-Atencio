package com.salazar.cafetecsup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.cafetecsup.ui.cliente.*
import com.salazar.cafetecsup.viewmodel.MenuViewModel
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel
import com.salazar.cafetecsup.viewmodel.SesionViewModel

@Composable
fun NavCafe(
    viewModel: SesionViewModel,
    menuViewModel: MenuViewModel,
    pedidosViewModel: PedidosClienteViewModel
) {
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
                },
                onIrAConfirmar = {
                    navController.navigate("confirmar_pedido")
                },
                onIrAPedidos = {
                    navController.navigate("mis_pedidos")
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

        composable("confirmar_pedido") {
            ConfirmarPedidoScreen(
                menuVm = menuViewModel,
                pedidosVm = pedidosViewModel,
                onVolverMenu = { navController.navigate("menu_cliente") { popUpTo("menu_cliente") { inclusive = true } } },
                onPedidoConfirmado = { nuevoId ->
                    navController.navigate("seguimiento/$nuevoId") {
                        popUpTo("menu_cliente")
                    }
                },
                onNavegarPedidos = { navController.navigate("mis_pedidos") }
            )
        }

        composable(
            route = "seguimiento/{pedidoId}",
            arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pedidoId = backStackEntry.arguments?.getInt("pedidoId") ?: 0
            SeguimientoScreen(
                pedidoId = pedidoId,
                pedidosVm = pedidosViewModel,
                onVolverMenu = { navController.navigate("menu_cliente") },
                onNavegarPedidos = { navController.navigate("mis_pedidos") }
            )
        }

        composable("mis_pedidos") {
            MisPedidosScreen(
                pedidosVm = pedidosViewModel,
                onVolverMenu = { navController.navigate("menu_cliente") },
                onVerSeguimiento = { pedidoId ->
                    navController.navigate("seguimiento/$pedidoId")
                }
            )
        }

        composable("panel_admin") {
            androidx.compose.material3.Text("Bienvenido al Panel de Administración")
        }
    }
}