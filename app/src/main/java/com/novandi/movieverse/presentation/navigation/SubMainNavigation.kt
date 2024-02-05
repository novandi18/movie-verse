package com.novandi.movieverse.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.novandi.movieverse.presentation.common.Screen
import com.novandi.movieverse.presentation.screen.MovieScreen
import com.novandi.movieverse.presentation.screen.SearchScreen

fun NavGraphBuilder.subMainGraph(navController: NavController) {
    navigation(startDestination = Screen.Movie.route, route = SubMainNavigation.SUB_MAIN_ROUTE) {
        composable(
            route = Screen.Movie.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            MovieScreen(
                movieId = movieId,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToMovie = { id ->
                    navController.navigate(
                        Screen.Movie.createRoute(
                            fromScreen = Screen.Home.route,
                            movieId = id
                        )
                    )
                }
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                navigateToMovie = { id ->
                    navController.navigate(
                        Screen.Movie.createRoute(
                            fromScreen = Screen.Search.route,
                            movieId = id
                        )
                    )
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

object SubMainNavigation {
    const val SUB_MAIN_ROUTE = "sub_main_route"
}