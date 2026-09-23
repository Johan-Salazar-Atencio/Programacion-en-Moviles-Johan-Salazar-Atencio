package com.salazar.tecsupfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.tecsupfit.screens.ConfirmationScreen
import com.salazar.tecsupfit.screens.DetailScreen
import com.salazar.tecsupfit.screens.HomeScreen
import com.salazar.tecsupfit.screens.sampleClasses

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onClassClick = { gymClass ->
                    navController.navigate(Screen.Detail.createRoute(gymClass.id))
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
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}