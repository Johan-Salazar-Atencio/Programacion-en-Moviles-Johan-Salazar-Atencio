package com.salazar.semana05_navegacion.navigation

sealed class Screen(val route: String) {

    //Desarrollado por Johan Salazar
    //Pantalla de inicio de sesión
    object Login : Screen(route = "login")

    //Pantalla de inicio
    object Home : Screen(route = "home")

    //Pantalla que muestra la lista de elementos
    object List : Screen(route = "list")

    //pantalla de perfil de usuario
    object Profile : Screen(route = "profile")

    //Ruta con Argumento
    object Detail : Screen(route = "detail/{itemId}") {

        //Construye la ruta final sustituyendo al placeholder por el valor real.
        //Ejemplo: createRoute(5) > devuelve "detail/5"
        fun createRoute(itemId: Int): String = "detail/$itemId"
    }
}
