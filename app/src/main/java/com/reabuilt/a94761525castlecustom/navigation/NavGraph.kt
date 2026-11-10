package com.reabuilt.a94761525castlecustom.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navGraph
import com.reabuilt.a94761525castlecustom.ui.detail.DetailScreen
import com.reabuilt.a94761525castlecustom.ui.downloads.DownloadsScreen
import com.reabuilt.a94761525castlecustom.ui.find.FindScreen
import com.reabuilt.a94761525castlecustom.ui.home.HomeScreen
import com.reabuilt.a94761525castlecustom.ui.livetv.LiveTvScreen
import com.reabuilt.a94761525castlecustom.ui.onboarding.OnboardingScreen
import com.reabuilt.a94761525castlecustom.ui.player.PlayerScreen
import com.reabuilt.a94761525castlecustom.ui.profile.ProfileScreen
import com.reabuilt.a94761525castlecustom.ui.search.SearchScreen
import com.reabuilt.a94761525castlecustom.ui.splash.SplashScreen

const val ROUTE_SPLASH = "splash"
const val ROUTE_ONBOARDING = "onboarding"
const val ROUTE_HOME = "home"
const val ROUTE_FIND = "find"
const val ROUTE_SEARCH = "search"
const val ROUTE_LIVE_TV = "live_tv"
const val ROUTE_DOWNLOADS = "downloads"
const val ROUTE_PROFILE = "profile"
const val ROUTE_DETAIL = "detail/{contentId}"
const val ROUTE_PLAYER = "player/{contentId}/{episodeId?}"
const val ROUTE_CHANNEL_PLAYER = "channel_player/{channelId}"

fun NavGraphBuilder.appNavGraph(
    onNavigateToHome: () -> Unit
) {
    composable(ROUTE_SPLASH) {
        SplashScreen(onNavigateToHome = onNavigateToHome)
    }

    composable(ROUTE_ONBOARDING) {
        OnboardingScreen(onComplete = onNavigateToHome)
    }

    navGraph(startDestination = ROUTE_HOME) {
        composable(ROUTE_HOME) {
            HomeScreen()
        }
        composable(ROUTE_FIND) {
            FindScreen()
        }
        composable(ROUTE_SEARCH) {
            SearchScreen()
        }
        composable(ROUTE_LIVE_TV) {
            LiveTvScreen()
        }
        composable(ROUTE_DOWNLOADS) {
            DownloadsScreen()
        }
        composable(ROUTE_PROFILE) {
            ProfileScreen()
        }
    }

    composable(
        route = ROUTE_DETAIL,
        arguments = listOf(androidx.navigation.navArgument("contentId") { type = androidx.navigation.NavType.StringType })
    ) { backStackEntry ->
        val contentId = backStackEntry.getString()!!
        DetailScreen(contentId = contentId)
    }

    composable(
        route = ROUTE_PLAYER,
        arguments = listOf(
            androidx.navigation.navArgument("contentId") { type = androidx.navigation.NavType.StringType },
            androidx.navigation.navArgument("episodeId") { type = androidx.navigation.NavType.StringType; defaultValue = "" }
        )
    ) { backStackEntry ->
        val contentId = backStackEntry.getString()!!
        val episodeId = backStackEntry.getString()
        PlayerScreen(contentId = contentId, episodeId = episodeId?.takeIf { it.isNotBlank() })
    }

    composable(
        route = ROUTE_CHANNEL_PLAYER,
        arguments = listOf(androidx.navigation.navArgument("channelId") { type = androidx.navigation.NavType.StringType })
    ) { backStackEntry ->
        val channelId = backStackEntry.getString()!!
        PlayerScreen(contentId = channelId, isLiveChannel = true)
    }
}
