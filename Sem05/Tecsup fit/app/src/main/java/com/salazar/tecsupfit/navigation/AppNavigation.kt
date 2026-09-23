package com.salazar.tecsupfit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.tecsupfit.screens.ConfirmationScreen
import com.salazar.tecsupfit.screens.DetailScreen
import com.salazar.tecsupfit.screens.HomeScreen
import com.salazar.tecsupfit.screens.Reservation
import com.salazar.tecsupfit.screens.ReservasScreen
import com.salazar.tecsupfit.screens.sampleClasses

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val userReservations = remember {
        mutableStateListOf(
            Reservation(1, "Cross Training", "Hoy, 6:00 pm", "Confirmada", true),
            Reservation(2, "Yoga funcional", "Ayer, 7:00 am", "Completada", false)
        )
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onClassClick = { gymClass ->
                    navController.navigate(Screen.Detail.createRoute(gymClass.id))
                },
                onNavigateToReservas = {
                    navController.navigate("reservas")
                }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("classId") { type = NavType.IntType })
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getInt("classId") ?: 1
            val gymClass = sampleClasses.find { it.id == classId } ?: sampleClasses.first()

            DetailScreen(
                gymClass = gymClass,
                onBackClick = { navController.popBackStack() },
                onReserveClick = { selectedClass ->
                    val exists = userReservations.any { it.className == selectedClass.name && it.isConfirmed }
                    if (!exists) {
                        userReservations.add(
                            0,
                            Reservation(
                                id = userReservations.size + 1,
                                className = selectedClass.name,
                                time = "Hoy, ${selectedClass.time}",
                                status = "Confirmada",
                                isConfirmed = true
                            )
                        )
                    }
                    navController.navigate("confirmation/${selectedClass.id}")
                }
            )
        }

        composable(
            route = "confirmation/{classId}",
            arguments = listOf(navArgument("classId") { type = NavType.IntType })
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getInt("classId") ?: 1
            val gymClass = sampleClasses.find { it.id == classId } ?: sampleClasses.first()

            ConfirmationScreen(
                className = gymClass.name,
                classTime = "Hoy, ${gymClass.time}",
                classRoom = gymClass.room,
                onViewReservationsClick = {
                    navController.navigate("reservas") {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable("reservas") {
            ReservasScreen(
                reservations = userReservations,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}