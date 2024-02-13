package com.novandi.movieverse.presentation.common

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Home : Screen("home")
    data object Trending : Screen("trending")
    data object Explore : Screen("explore")
    data object User : Screen("user")
    data object Search : Screen("search")
    data object Movie : Screen("{fromScreen}/{movieId}") {
        fun createRoute(fromScreen: String, movieId: Int) = "$fromScreen/$movieId"
    }
}