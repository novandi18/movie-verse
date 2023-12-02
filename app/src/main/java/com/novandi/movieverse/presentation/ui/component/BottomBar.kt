package com.novandi.movieverse.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.common.NavigationItem
import com.novandi.movieverse.presentation.common.Screen
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.White

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screens = listOf(
        NavigationItem(
            title = stringResource(id = R.string.home),
            icon = Icons.Rounded.Home,
            screen = Screen.Home,
            contentDescription = stringResource(id = R.string.home)
        ),
        NavigationItem(
            title = stringResource(id = R.string.explore),
            icon = Icons.Rounded.Explore,
            screen = Screen.Explore,
            contentDescription = stringResource(id = R.string.explore)
        ),
        NavigationItem(
            title = stringResource(id = R.string.trending),
            icon = Icons.Rounded.LocalFireDepartment,
            screen = Screen.Trending,
            contentDescription = stringResource(id = R.string.trending)
        )
    )

    NavigationBar(
        containerColor = Black,
        contentColor = White
    ) {
        screens.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription
                    )
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Gray,
                    selectedIconColor = White,
                    indicatorColor = Black
                )
            )
        }
    }
}