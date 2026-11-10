package com.reabuilt.a94761525castlecustom.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_DOWNLOADS
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_FIND
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_HOME
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_LIVE_TV
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_ONBOARDING
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_PROFILE
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_SPLASH
import com.reabuilt.a94761525castlecustom.navigation.appNavGraph
import com.reabuilt.a94761525castlecustom.viewmodel.MainViewModel

@Composable
fun CastleApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val mainTabRoutes = setOf(ROUTE_HOME, ROUTE_FIND, ROUTE_LIVE_TV, ROUTE_DOWNLOADS, ROUTE_PROFILE)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (currentRoute in mainTabRoutes) {
                CastleBottomBar(currentRoute = currentRoute) { targetRoute ->
                    if (targetRoute != currentRoute) {
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            appNavGraph(
                onNavigateToHome = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate(ROUTE_ONBOARDING) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun CastleBottomBar(
    currentRoute: String?,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(color = MaterialTheme.colorScheme.surface) {
        bottomItem(ROUTE_HOME, Icons.Filled.Home, "Home", currentRoute, onTabSelected)
        bottomItem(ROUTE_FIND, Icons.Filled.Explore, "Find", currentRoute, onTabSelected)
        bottomItem(ROUTE_LIVE_TV, Icons.Filled.LiveTv, "Live TV", currentRoute, onTabSelected)
        bottomItem(ROUTE_DOWNLOADS, Icons.Filled.Download, "Downloads", currentRoute, onTabSelected)
        bottomItem(ROUTE_PROFILE, Icons.Filled.Person, "Profile", currentRoute, onTabSelected)
    }
}

@Composable
private fun NavigationBarScope.bottomItem(
    route: String,
    icon: ImageVector,
    label: String,
    currentRoute: String?,
    onTabSelected: (String) -> Unit
) {
    NavigationBarItem(
        selected = currentRoute == route,
        onClick = { onTabSelected(route) },
        icon = { Icon(imageVector = icon, contentDescription = label) },
        label = { Text(label) }
    )
}
