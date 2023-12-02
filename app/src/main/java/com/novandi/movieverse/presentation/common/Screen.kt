package com.novandi.movieverse.presentation.common

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Trending : Screen("trending")
    data object Explore : Screen("explore")
}