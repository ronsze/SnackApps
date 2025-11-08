package kr.sdbk.snackapps

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.sdbk.snackapps.composable.AppState
import kr.sdbk.snackapps.navigation.SnackAppsNavigation

@Composable
internal fun SnackApps(
    appState: AppState,
    modifier: Modifier
) {
    Surface(modifier) {
        SnackAppsNavigation(appState.navController)
    }
}