package com.salazar.tecsupfit.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{classId}") {
        fun createRoute(classId: Int) = "detail/$classId"
    }
    object Confirmation : Screen("confirmation/{className}/{classTime}/{classRoom}") {
        fun createRoute(name: String, time: String, room: String) = "confirmation/$name/$time/$room"
    }
    object Reservas : Screen("reservas")
    object Rutinas : Screen("rutinas")
    object Profile : Screen("profile")
}
