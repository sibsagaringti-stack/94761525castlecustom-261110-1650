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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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

@Composable
fun CastleApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarVisible = currentRoute in listOf(
        ROUTE_HOME,
        ROUTE_FIND,
        ROUTE_LIVE_TV,
        ROUTE_DOWNLOADS,
        ROUTE_PROFILE
    )

    Scaffold(
        bottomBar = {
            if (bottomBarVisible) {
                CastleBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            appNavGraph(
                onNavigateToHome = { navController.navigate(ROUTE_HOME) },
                onNavigateToOnboarding = { navController.navigate(ROUTE_ONBOARDING) }
            )
        }
    }
}

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(ROUTE_HOME, "Home", Icons.Filled.Home),
    BottomNavItem(ROUTE_FIND, "Find", Icons.Filled.Explore),
    BottomNavItem(ROUTE_LIVE_TV, "Live TV", Icons.Filled.LiveTv),
    BottomNavItem(ROUTE_DOWNLOADS, "Downloads", Icons.Filled.Download),
    BottomNavItem(ROUTE_PROFILE, "Profile", Icons.Filled.Person)
)

@Composable
private fun CastleBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
