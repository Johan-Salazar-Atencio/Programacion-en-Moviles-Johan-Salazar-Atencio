package com.salazar.cafetecsup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.cafetecsup.ui.admin.AjustesAdminScreen
import com.salazar.cafetecsup.ui.admin.DetallePedidoAdminScreen
import com.salazar.cafetecsup.ui.admin.FormularioProductoScreen
import com.salazar.cafetecsup.ui.admin.MenuAdminScreen
import com.salazar.cafetecsup.ui.admin.PanelScreen
import com.salazar.cafetecsup.ui.admin.PedidosAdminScreen
import com.salazar.cafetecsup.ui.cliente.*
import com.salazar.cafetecsup.viewmodel.MenuAdminViewModel
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
    pedidosAdminViewModel: PedidosAdminViewModel,
    menuAdminViewModel: MenuAdminViewModel
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
                onIrAPerfil = {
                    navController.navigate("perfil_cliente")
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
                viewModel = viewModel,
                onVolverMenu = { navController.navigate("menu_cliente") { popUpTo("menu_cliente") { inclusive = true } } },
                onPedidoConfirmado = { nuevoId ->
                    navController.navigate("seguimiento/$nuevoId") {
                        popUpTo("menu_cliente")
                    }
                },
                onNavegarPedidos = { navController.navigate("mis_pedidos") },
                onIrAPerfil = { navController.navigate("perfil_cliente") }
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
                onNavegarPedidos = { navController.navigate("mis_pedidos") },
                onIrAPerfil = { navController.navigate("perfil_cliente") }
            )
        }

        composable("mis_pedidos") {
            MisPedidosScreen(
                pedidosVm = pedidosViewModel,
                onVolverMenu = { navController.navigate("menu_cliente") },
                onVerSeguimiento = { pedidoId ->
                    navController.navigate("seguimiento/$pedidoId")
                },
                onIrAPerfil = { navController.navigate("perfil_cliente") }
            )
        }

        composable("perfil_cliente") {
            PerfilClienteScreen(
                viewModel = viewModel,
                onVolverMenu = { navController.navigate("menu_cliente") { popUpTo("menu_cliente") { inclusive = true } } },
                onNavegarPedidos = { navController.navigate("mis_pedidos") },
                onCambiarAAdmin = {
                    viewModel.cambiarAAdmin()
                    navController.navigate("panel_admin") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onCerrarSesion = {
                    viewModel.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("panel_admin") {
            PanelScreen(
                vm = panelAdminViewModel,
                onNavegarPedidos = { navController.navigate("pedidos_admin") },
                onNavegarMenu = { navController.navigate("menu_admin") },
                onNavegarAjustes = { navController.navigate("ajustes_admin") },
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
                onNavegarMenu = { navController.navigate("menu_admin") },
                onNavegarAjustes = { navController.navigate("ajustes_admin") },
                onVerDetalle = { id ->
                    navController.navigate("detalle_pedido_admin/$id")
                }
            )
        }

        composable("menu_admin") {
            MenuAdminScreen(
                vm = menuAdminViewModel,
                onNavegarPanel = { navController.navigate("panel_admin") { popUpTo("panel_admin") { inclusive = true } } },
                onNavegarPedidos = { navController.navigate("pedidos_admin") },
                onNavegarMenu = { },
                onNavegarAjustes = { navController.navigate("ajustes_admin") },
                onAgregarProducto = { navController.navigate("formulario_producto/0") },
                onEditarProducto = { id -> navController.navigate("formulario_producto/$id") }
            )
        }

        composable("ajustes_admin") {
            AjustesAdminScreen(
                viewModel = viewModel,
                onNavegarPanel = { navController.navigate("panel_admin") { popUpTo("panel_admin") { inclusive = true } } },
                onNavegarPedidos = { navController.navigate("pedidos_admin") },
                onNavegarMenu = { navController.navigate("menu_admin") },
                onCambiarACliente = {
                    navController.navigate("menu_cliente") {
                        popUpTo(0) { inclusive = true }
                    }
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
            route = "formulario_producto/{productoId}",
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            FormularioProductoScreen(
                productoId = productoId,
                vm = menuAdminViewModel,
                onVolver = { navController.popBackStack() }
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
