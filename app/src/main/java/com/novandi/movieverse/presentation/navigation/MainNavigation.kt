package com.novandi.movieverse.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.novandi.movieverse.presentation.common.Screen
import com.novandi.movieverse.presentation.screen.HomeScreen

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.route, route = MainNavigation.MAIN_ROUTE) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}

object MainNavigation {
    const val MAIN_ROUTE = "main_route"
}