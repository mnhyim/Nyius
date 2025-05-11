package com.mnhyim.nyius.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mnhyim.nyius.ui.feature.home.HomeScreen
import com.mnhyim.nyius.ui.feature.sources.SourcesScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
    ) {
        composable<Routes.Home> {
            HomeScreen(
                onNavigate = navController::navigate,
                modifier = modifier
            )
        }
        composable<Routes.Sources> {
            SourcesScreen(
                onNavigate = navController::navigate,
                modifier = modifier
            )
        }
    }
}