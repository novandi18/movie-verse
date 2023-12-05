package com.novandi.movieverse.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.novandi.movieverse.presentation.common.Screen
import com.novandi.movieverse.presentation.screen.MovieScreen

fun NavGraphBuilder.subMainGraph(navController: NavController) {
    navigation(startDestination = Screen.Movie.route, route = SubMainNavigation.SUB_MAIN_ROUTE) {
        composable(
            route = Screen.Movie.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            MovieScreen(movieId)
        }
    }
}

object SubMainNavigation {
    const val SUB_MAIN_ROUTE = "sub_main_route"
}