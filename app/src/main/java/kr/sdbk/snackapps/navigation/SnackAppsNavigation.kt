package kr.sdbk.snackapps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kr.sdbk.clock.clockGraph
import kr.sdbk.snackapps.composable.MainView

@Composable
internal fun SnackAppsNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        composable<MainRoute> {
            MainView { navController.navigate(it.dest) }
        }

        clockGraph(navController)
    }
}

@Serializable internal data object MainRoute