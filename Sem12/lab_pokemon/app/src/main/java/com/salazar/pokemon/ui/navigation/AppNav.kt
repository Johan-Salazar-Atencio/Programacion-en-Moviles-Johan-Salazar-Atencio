package com.salazar.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.pokemon.AppContainer
import com.salazar.pokemon.ui.SesionViewModel
import com.salazar.pokemon.ui.UiState
import com.salazar.pokemon.ui.ViewModelFactory
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
private const val RUTA_DETALLE = "detalle/{id}"

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
    val destinoInicial = if (inicial.datos != null) RUTA_LISTA else RUTA_LOGIN

    NavHost(navController = navController, startDestination = destinoInicial) {
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
                onFavoritos = { navController.navigate(RUTA_FAVORITOS) },
                onCerrarSesion = {
                    sesionViewModel.cerrarSesion()
                    navController.navigate(RUTA_LOGIN) { popUpTo(RUTA_LISTA) { inclusive = true } }
                }
            )
        }
        composable(RUTA_FAVORITOS) {
            val viewModel: FavoritosViewModel = viewModel(factory = factory)
            FavoritosScreen(
                viewModel,
                onItemClick = { id -> navController.navigate("detalle/$id") },
                onVolver = { navController.popBackStack() }
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
