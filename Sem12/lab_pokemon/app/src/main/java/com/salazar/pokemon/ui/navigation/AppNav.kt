package com.salazar.pokemon.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.pokemon.AppContainer
import com.salazar.pokemon.domain.model.Rol
import com.salazar.pokemon.ui.SesionViewModel
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.ViewModelFactory
import com.salazar.pokemon.ui.admin.AdminScreen
import com.salazar.pokemon.ui.admin.AdminViewModel
import com.salazar.pokemon.ui.components.Cargando
import com.salazar.pokemon.ui.detalle.DetalleScreen
import com.salazar.pokemon.ui.detalle.DetalleViewModel
import com.salazar.pokemon.ui.favoritos.FavoritosScreen
import com.salazar.pokemon.ui.favoritos.FavoritosViewModel
import com.salazar.pokemon.ui.lista.ListaScreen
import com.salazar.pokemon.ui.lista.ListaViewModel
import com.salazar.pokemon.ui.login.LoginScreen
import com.salazar.pokemon.ui.login.LoginViewModel

private const val RUTA_LOGIN = "login"
private const val RUTA_LISTA = "lista"
private const val RUTA_FAVORITOS = "favoritos"
private const val RUTA_ADMIN = "admin"
private const val RUTA_DETALLE = "detalle/{id}"

private class Destino(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector,
    val iconoActivo: ImageVector
)

private val DestinoLista = Destino(RUTA_LISTA, "Pokémon", Icons.Outlined.Home, Icons.Filled.Home)
private val DestinoFavoritos =
    Destino(RUTA_FAVORITOS, "Favoritos", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)
private val DestinoAdmin = Destino(RUTA_ADMIN, "Panel", Icons.Outlined.Settings, Icons.Filled.Settings)

@Composable
fun AppNav(container: AppContainer) {
    val factory = ViewModelFactory(container)
    val sesionViewModel: SesionViewModel = viewModel(factory = factory)
    val sesion by sesionViewModel.sesion.collectAsStateWithLifecycle()

    // se espera a conocer si hay sesión para elegir la pantalla inicial
    val inicial = sesion as? UiState.Exito
    if (inicial == null) {
        Cargando()
        return
    }

    val navController = rememberNavController()
    val esAdmin = inicial.datos?.rol == Rol.ADMIN
    val destinoInicial = if (inicial.datos != null) RUTA_LISTA else RUTA_LOGIN
    val destinos = if (esAdmin) listOf(DestinoLista, DestinoFavoritos, DestinoAdmin)
    else listOf(DestinoLista, DestinoFavoritos)

    val entrada by navController.currentBackStackEntryAsState()
    val actual = entrada?.destination
    val mostrarBarra = actual?.hierarchy?.any { d -> destinos.any { it.ruta == d.route } } == true

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (mostrarBarra) {
                NavigationBar {
                    destinos.forEach { destino ->
                        val activo = actual?.hierarchy?.any { it.route == destino.ruta } == true
                        NavigationBarItem(
                            selected = activo,
                            onClick = { navController.irA(destino.ruta) },
                            icon = {
                                Icon(
                                    if (activo) destino.iconoActivo else destino.icono,
                                    contentDescription = destino.titulo
                                )
                            },
                            label = { Text(destino.titulo) }
                        )
                    }
                }
            }
        }
    ) { relleno ->
        val inferior = relleno.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = destinoInicial,
            modifier = Modifier
                .padding(bottom = inferior)
                .consumeWindowInsets(PaddingValues(bottom = inferior))
        ) {
            composable(RUTA_LOGIN) {
                val viewModel: LoginViewModel = viewModel(factory = factory)
                LoginScreen(viewModel, onLoginExitoso = {
                    sesionViewModel.recargar()
                    navController.navigate(RUTA_LISTA) { popUpTo(RUTA_LOGIN) { inclusive = true } }
                })
            }
            composable(RUTA_LISTA) {
                val viewModel: ListaViewModel = viewModel(factory = factory)
                ListaScreen(
                    viewModel,
                    onItemClick = { id -> navController.navigate("detalle/$id") },
                    onCerrarSesion = {
                        sesionViewModel.cerrarSesion()
                        navController.navigate(RUTA_LOGIN) { popUpTo(0) { inclusive = true } }
                    }
                )
            }
            composable(RUTA_ADMIN) {
                if (esAdmin) {
                    val viewModel: AdminViewModel = viewModel(factory = factory)
                    AdminScreen(viewModel)
                } else {
                    LaunchedEffect(Unit) { navController.popBackStack() }
                }
            }
            composable(RUTA_FAVORITOS) {
                val viewModel: FavoritosViewModel = viewModel(factory = factory)
                FavoritosScreen(
                    viewModel,
                    onItemClick = { id -> navController.navigate("detalle/$id") }
                )
            }
            composable(
                route = RUTA_DETALLE,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val viewModel: DetalleViewModel = viewModel(factory = factory)
                DetalleScreen(viewModel, onVolver = { navController.popBackStack() })
            }
        }
    }
}

private fun NavHostController.irA(ruta: String) {
    navigate(ruta) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
