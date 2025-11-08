package kr.sdbk.snackapps.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberAppState(
    navController: NavHostController = rememberNavController()
) : AppState {
    val state = remember(
        navController
    ) {
        AppState(
            navController = navController
        )
    }

    return state
}

class AppState(
    val navController: NavHostController
) {
}