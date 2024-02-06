package com.novandi.movieverse.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.novandi.movieverse.presentation.navigation.mainGraph
import com.novandi.movieverse.presentation.navigation.subMainGraph
import com.novandi.movieverse.presentation.navigation.welcomeGraph
import com.novandi.movieverse.presentation.ui.component.BottomBar

@Composable
fun MovieVerseApp(
    navHostController: NavHostController = rememberNavController(),
    startedDestination: String
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navHostController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            modifier = Modifier.padding(paddingValues),
            startDestination = startedDestination
        ) {
            welcomeGraph(navHostController)
            mainGraph(navHostController)
            subMainGraph(navHostController)
        }
    }
}