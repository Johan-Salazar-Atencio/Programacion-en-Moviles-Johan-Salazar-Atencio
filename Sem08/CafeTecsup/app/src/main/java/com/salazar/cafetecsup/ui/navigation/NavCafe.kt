package com.salazar.cafetecsup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.cafetecsup.ui.admin.DetallePedidoAdminScreen
import com.salazar.cafetecsup.ui.admin.PanelScreen
import com.salazar.cafetecsup.ui.admin.PedidosAdminScreen
import com.salazar.cafetecsup.ui.cliente.*
import com.salazar.cafetecsup.viewmodel.MenuViewModel
import com.salazar.cafetecsup.viewmodel.PanelAdminViewModel
import com.salazar.cafetecsup.viewmodel.PedidosAdminViewModel
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel
import com.salazar.cafetecsup.viewmodel.SesionViewModel

@Composable
fun NavCafe(
    viewModel: SesionViewModel,
    menuViewModel: MenuViewModel,
    pedidosViewModel: PedidosClienteViewModel,
    panelAdminViewModel: PanelAdminViewModel,
    pedidosAdminViewModel: PedidosAdminViewModel
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
                },
                onCerrarSesion = {
                    viewModel.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
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
            PanelScreen(
                vm = panelAdminViewModel,
                onNavegarPedidos = { navController.navigate("pedidos_admin") },
                onCerrarSesion = {
                    viewModel.cerrarSesion()
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable("pedidos_admin") {
            PedidosAdminScreen(
                vm = pedidosAdminViewModel,
                onVolverPanel = { navController.popBackStack() }
            )
        }
        composable("panel_admin") {
            PanelScreen(
                vm = panelAdminViewModel,
                onNavegarPedidos = { navController.navigate("pedidos_admin") },
                onCerrarSesion = {
                    viewModel.cerrarSesion()
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                },
                onVerDetalle = { id ->
                    navController.navigate("detalle_pedido_admin/$id")
                }
            )
        }

        composable("pedidos_admin") {
            PedidosAdminScreen(
                vm = pedidosAdminViewModel,
                onVolverPanel = { navController.popBackStack() },
                onVerDetalle = { id ->
                    navController.navigate("detalle_pedido_admin/$id")
                }
            )
        }

        composable(
            route = "detalle_pedido_admin/{pedidoId}",
            arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pedidoId = backStackEntry.arguments?.getInt("pedidoId") ?: 0
            DetallePedidoAdminScreen(
                pedidoId = pedidoId,
                vm = pedidosAdminViewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}