package com.novandi.movieverse.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.novandi.movieverse.presentation.navigation.MainNavigation
import com.novandi.movieverse.presentation.navigation.mainGraph

@Composable
fun MovieVerseApp(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold { paddingValues ->
        NavHost(
            navController = navHostController,
            modifier = Modifier.padding(paddingValues),
            startDestination = MainNavigation.MAIN_ROUTE
        ) {
            mainGraph(navHostController)
        }
    }
}