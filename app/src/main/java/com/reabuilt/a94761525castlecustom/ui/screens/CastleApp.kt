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
import com.reabuilt.a94761525castlecustom.CastleApplication
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
import com.reabuilt.a94761525castlecustom.ui.search.SearchScreen
import com.reabuilt.a94761525castlecustom.ui.detail.DetailScreen
import com.reabuilt.a94761525castlecustom.ui.player.PlayerScreen
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Home
import androidx.compose.material3.icons.filled.Search
import androidx.compose.material3.icons.filled.LiveTv
import androidx.compose.material3.icons.filled.Download
import androidx.compose.material3.icons.filled.Person

@Composable
fun CastleApp(application: CastleApplication) {
    val navController = rememberNavController()
    val viewModel: CastleViewModel = viewModel(
        factory = CastleViewModelFactory(application)
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val bottomNavRoutes = listOf(ROUTE_HOME, ROUTE_FIND, ROUTE_LIVE_TV, ROUTE_DOWNLOADS, ROUTE_PROFILE)
    val showBottomBar = bottomNavRoutes.any { route ->
        currentRoute?.startsWith(route) == true
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_HOME,
                        onClick = { navController.navigate(ROUTE_HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_FIND,
                        onClick = { navController.navigate(ROUTE_FIND) },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Find") },
                        label = { Text("Find") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_LIVE_TV,
                        onClick = { navController.navigate(ROUTE_LIVE_TV) },
                        icon = { Icon(Icons.Default.LiveTv, contentDescription = "Live TV") },
                        label = { Text("Live TV") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_DOWNLOADS,
                        onClick = { navController.navigate(ROUTE_DOWNLOADS) },
                        icon = { Icon(Icons.Default.Download, contentDescription = "Downloads") },
                        label = { Text("Downloads") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_PROFILE,
                        onClick = { navController.navigate(ROUTE_PROFILE) },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { padding ->
        // The navigation graph is set up in the separate file
        // This function is just for setting up the bottom bar and scaffold
    }
}
