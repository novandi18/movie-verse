package com.novandi.movieverse.presentation.common

sealed class Screen(val route: String) {
    data object Home : Screen("home")
}