package com.reabuilt.a94761525castlecustom.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.reabuilt.a94761525castlecustom.ui.detail.DetailScreen
import com.reabuilt.a94761525castlecustom.ui.home.HomeScreen
import com.reabuilt.a94761525castlecustom.ui.livetv.LiveTvScreen
import com.reabuilt.a94761525castlecustom.ui.player.PlayerScreen
import com.reabuilt.a94761525castlecustom.ui.profile.ProfileScreen
import com.reabuilt.a94761525castlecustom.ui.search.SearchScreen

const val ROUTE_HOME = "home"
const val ROUTE_LIVE_TV = "livetv"
const val ROUTE_SEARCH = "search"
const val ROUTE_PROFILE = "profile"
const val ROUTE_PLAYER = "player/{contentId}"
const val ROUTE_DETAIL = "detail/{contentId}"
const val ROUTE_CHANNEL_PLAYER = "channel_player/{channelId}"

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun CastleNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(ROUTE_HOME, "Home", Icons.Filled.Home),
        BottomNavItem(ROUTE_LIVE_TV, "Live TV", Icons.Filled.Tv),
        BottomNavItem(ROUTE_PROFILE, "Profile", Icons.Filled.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_HOME) {
                HomeScreen(navController = navController)
            }
            composable(ROUTE_LIVE_TV) {
                LiveTvScreen(navController = navController)
            }
            composable(ROUTE_SEARCH) {
                SearchScreen(navController = navController)
            }
            composable(ROUTE_PROFILE) {
                ProfileScreen(navController = navController)
            }
            composable(
                route = ROUTE_PLAYER,
                arguments = listOf(
                    navArgument("contentId") { type = NavType.StringType },
                    navArgument("episodeId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("isLiveChannel") {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { backStackEntry ->
                PlayerScreen(
                    navController = navController,
                    contentId = backStackEntry.arguments?.getString("contentId") ?: "",
                    episodeId = backStackEntry.arguments?.getString("episodeId"),
                    isLiveChannel = backStackEntry.arguments?.getBoolean("isLiveChannel") ?: false
                )
            }
            composable(
                route = ROUTE_DETAIL,
                arguments = listOf(
                    navArgument("contentId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                DetailScreen(
                    navController = navController,
                    contentId = backStackEntry.arguments?.getString("contentId") ?: ""
                )
            }
            composable(
                route = ROUTE_CHANNEL_PLAYER,
                arguments = listOf(
                    navArgument("channelId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                PlayerScreen(
                    navController = navController,
                    contentId = backStackEntry.arguments?.getString("channelId") ?: "",
                    isLiveChannel = true
                )
            }
        }
    }
}
