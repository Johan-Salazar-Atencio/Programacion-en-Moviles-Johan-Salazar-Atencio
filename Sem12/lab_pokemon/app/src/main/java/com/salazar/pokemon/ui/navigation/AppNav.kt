package com.salazar.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.pokemon.AppContainer
import com.salazar.pokemon.ui.ViewModelFactory
import com.salazar.pokemon.ui.detalle.DetalleScreen
import com.salazar.pokemon.ui.detalle.DetalleViewModel
import com.salazar.pokemon.ui.favoritos.FavoritosScreen
import com.salazar.pokemon.ui.favoritos.FavoritosViewModel
import com.salazar.pokemon.ui.lista.ListaScreen
import com.salazar.pokemon.ui.lista.ListaViewModel

private const val RUTA_LISTA = "lista"
private const val RUTA_FAVORITOS = "favoritos"
private const val RUTA_DETALLE = "detalle/{id}"

@Composable
fun AppNav(container: AppContainer) {
    val navController = rememberNavController()
    val factory = ViewModelFactory(container)

    NavHost(navController = navController, startDestination = RUTA_LISTA) {
        composable(RUTA_LISTA) {
            val viewModel: ListaViewModel = viewModel(factory = factory)
            ListaScreen(
                viewModel,
                onItemClick = { id -> navController.navigate("detalle/$id") },
                onFavoritos = { navController.navigate(RUTA_FAVORITOS) }
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
