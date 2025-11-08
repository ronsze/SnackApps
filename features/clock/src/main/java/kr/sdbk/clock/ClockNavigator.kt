package kr.sdbk.clock

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavController.navigateToClock() = navigate(ClockRoute)

fun NavGraphBuilder.clockGraph(
    navController: NavController
) {
    composable<ClockRoute> {
        val events = remember {
            ClockEvents(
                popBackStack = navController::popBackStack
            )
        }
        ClockView(events)
    }
}

@Serializable data object ClockRoute