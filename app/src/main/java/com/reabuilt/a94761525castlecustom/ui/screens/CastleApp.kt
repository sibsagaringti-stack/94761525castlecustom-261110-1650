package com.reabuilt.a94761525castlecustom.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reabuilt.a94761525castlecustom.CastleViewModel
import com.reabuilt.a94761525castlecustom.CastleViewModelFactory
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_DETAIL
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_DOWNLOADS
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_FIND
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_HOME
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_LIVE_TV
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_PLAYER
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_PROFILE
import com.reabuilt.a94761525castlecustom.navigation.ROUTE_SEARCH
import com.reabuilt.a94761525castlecustom.navigation.appNavGraph
import com.reabuilt.a94761525castlecustom.ui.home.HomeScreen
import com.reabuilt.a94761525castlecustom.ui.find.FindScreen
import com.reabuilt.a94761525castlecustom.ui.livetv.LiveTvScreen
import com.reabuilt.a94761525castlecustom.ui.downloads.DownloadsScreen
import com.reabuilt.a94761525castlecustom.ui.profile.ProfileScreen
import com.reabuilt.a94761525castlecustom.ui.detail.DetailScreen
import com.reabuilt.a94761525castlecustom.ui.player.PlayerScreen
import com.reabuilt.a94761525castlecustom.ui.search.SearchScreen
import com.reabuilt.a94761525castlecustom.ui.splash.SplashScreen

@Composable
fun CastleApp() {
    val navController = rememberNavController()
    val viewModel: CastleViewModel = viewModel(
        factory = CastleViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext as com.reabuilt.a94761525castlecustom.CastleApplication
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(ROUTE_HOME, "Home", androidx.compose.material.icons.Icons.Filled.Home, androidx.compose.material.icons.Icons.Outlined.Home),
        BottomNavItem(ROUTE_FIND, "Find", androidx.compose.material.icons.Icons.Filled.Search, androidx.compose.material.icons.Icons.Outlined.Search),
        BottomNavItem(ROUTE_LIVE_TV, "Live TV", androidx.compose.material.icons.Icons.Filled.LiveTv, androidx.compose.material.icons.Icons.Outlined.LiveTv),
        BottomNavItem(ROUTE_DOWNLOADS, "Downloads", androidx.compose.material.icons.Icons.Filled.Download, androidx.compose.material.icons.Icons.Outlined.Download),
        BottomNavItem(ROUTE_PROFILE, "Profile", androidx.compose.material.icons.Icons.Filled.Person, androidx.compose.material.icons.Icons.Outlined.Person)
    )

    val showBottomBar = currentRoute in listOf(ROUTE_HOME, ROUTE_FIND, ROUTE_LIVE_TV, ROUTE_DOWNLOADS, ROUTE_PROFILE)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    navBackStackEntry?.destination?.route?.let { route ->
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (route == item.route) item.filledIcon else item.outlinedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label) },
                                selected = route == item.route,
                                onClick = {
                                    if (route != item.route) {
                                        navController.navigate(item.route) {
                                            popUpTo(ROUTE_HOME)
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        androidx.navigation.compose.NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") {
                SplashScreen(
                    onNavigateToHome = {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }
            composable(ROUTE_HOME) {
                HomeScreen(
                    onContentClick = { contentId ->
                        navController.navigate("$ROUTE_DETAIL/$contentId")
                    },
                    onNavigateToLiveTv = {
                        navController.navigate(ROUTE_LIVE_TV)
                    }
                )
            }
            composable(ROUTE_FIND) {
                FindScreen(
                    onContentClick = { contentId ->
                        navController.navigate("$ROUTE_DETAIL/$contentId")
                    }
                )
            }
            composable(ROUTE_LIVE_TV) {
                LiveTvScreen(
                    onChannelClick = { channelId ->
                        navController.navigate("channel_player/$channelId")
                    }
                )
            }
            composable(ROUTE_DOWNLOADS) {
                DownloadsScreen(
                    onContentClick = { contentId ->
                        navController.navigate("$ROUTE_DETAIL/$contentId")
                    }
                )
            }
            composable(ROUTE_PROFILE) {
                ProfileScreen()
            }
            composable(
                route = "$ROUTE_DETAIL/{contentId}",
                arguments = listOf(androidx.navigation.navArgument("contentId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val contentId = backStackEntry.arguments?.getString("contentId") ?: return@composable
                DetailScreen(
                    contentId = contentId,
                    onNavigateBack = { navController.popBackStack() },
                    onPlayClick = { contentId, episodeId ->
                        val route = if (episodeId != null) {
                            "$ROUTE_PLAYER/$contentId/$episodeId"
                        } else {
                            "$ROUTE_PLAYER/$contentId"
                        }
                        navController.navigate(route)
                    }
                )
            }
            composable(
                route = "$ROUTE_PLAYER/{contentId}/{episodeId?}",
                arguments = listOf(
                    androidx.navigation.navArgument("contentId") { type = androidx.navigation.NavType.StringType },
                    androidx.navigation.navArgument("episodeId") {
                        type = androidx.navigation.NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) { backStackEntry ->
                val contentId = backStackEntry.arguments?.getString("contentId") ?: return@composable
                val episodeId = backStackEntry.arguments?.getString("episodeId")
                PlayerScreen(
                    contentId = contentId,
                    episodeId = episodeId?.takeIf { it.isNotEmpty() },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "channel_player/{channelId}",
                arguments = listOf(androidx.navigation.navArgument("channelId") { type = androidx.navigation.NavType.StringType })
            ) { backStackEntry ->
                val channelId = backStackEntry.arguments?.getString("channelId") ?: return@composable
                PlayerScreen(
                    contentId = channelId,
                    isLiveChannel = true,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val filledIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val outlinedIcon: androidx.compose.ui.graphics.vector.ImageVector
)
