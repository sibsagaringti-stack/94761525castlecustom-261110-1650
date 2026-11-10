package com.reabuilt.a94761525castlecustom.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = listOf(ROUTE_HOME, ROUTE_FIND, ROUTE_LIVE_TV, ROUTE_DOWNLOADS, ROUTE_PROFILE)
    val showBottomBar = bottomNavRoutes.any { route -> currentRoute?.startsWith(route) == true }

    val onNavigateToHome: () -> Unit = {
        navController.navigate(ROUTE_HOME) {
            popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
            launchSingleTop = true
        }
    }
    val onNavigateToOnboarding: () -> Unit = {
        navController.navigate(ROUTE_ONBOARDING) {
            popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
        }
    }

    fun navigateToTab(tabRoute: String) {
        if (currentRoute == tabRoute) return
        navController.navigate(tabRoute) {
            popUpTo(tabRoute) { inclusive = false }
            launchSingleTop = true
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_HOME,
                        onClick = { navigateToTab(ROUTE_HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_FIND,
                        onClick = { navigateToTab(ROUTE_FIND) },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Find") },
                        label = { Text("Find") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_LIVE_TV,
                        onClick = { navigateToTab(ROUTE_LIVE_TV) },
                        icon = { Icon(Icons.Default.LiveTv, contentDescription = "Live TV") },
                        label = { Text("Live TV") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_DOWNLOADS,
                        onClick = { navigateToTab(ROUTE_DOWNLOADS) },
                        icon = { Icon(Icons.Default.Download, contentDescription = "Downloads") },
                        label = { Text("Downloads") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_PROFILE,
                        onClick = { navigateToTab(ROUTE_PROFILE) },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") }
                    )
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
                onNavigateToHome = onNavigateToHome,
                onNavigateToOnboarding = onNavigateToOnboarding
            )
        }
    }
}
