package com.novandi.movieverse.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.novandi.movieverse.presentation.common.Screen
import com.novandi.movieverse.presentation.screen.ExploreScreen
import com.novandi.movieverse.presentation.screen.HomeScreen
import com.novandi.movieverse.presentation.screen.TrendingScreen
import com.novandi.movieverse.presentation.screen.UserScreen

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.route, route = MainNavigation.MAIN_ROUTE) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToMovie = { id ->
                    navController.navigate(
                        Screen.Movie.createRoute(
                            fromScreen = Screen.Home.route,
                            movieId = id
                        )
                    )
                },
                navigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        composable(Screen.Trending.route) {
            TrendingScreen(
                navigateToMovie = { id ->
                    navController.navigate(
                        Screen.Movie.createRoute(
                            fromScreen = Screen.Trending.route,
                            movieId = id
                        )
                    )
                }
            )
        }
        composable(Screen.Explore.route) {
            ExploreScreen(
                navigateToMovie = { id ->
                    navController.navigate(
                        Screen.Movie.createRoute(
                            fromScreen = Screen.Explore.route,
                            movieId = id
                        )
                    )
                },
                navigateToSearch = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        composable(Screen.User.route) {
            UserScreen()
        }
    }
}

object MainNavigation {
    const val MAIN_ROUTE = "main_route"
}