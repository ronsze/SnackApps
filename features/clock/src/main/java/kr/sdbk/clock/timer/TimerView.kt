package kr.sdbk.clock.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.sdbk.clock.R
import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.clock.timer.composable.TimeSetSection
import kr.sdbk.clock.timer.composable.TimerClock
import kr.sdbk.design_system.component.spacer.VerticalSpacer
import kr.sdbk.design_system.preview.FullScreenPreview
import kr.sdbk.platform_extension.functions.showToast

data class TimerUiEvents(
    val onClickTimer: (TimerStatus) -> Unit,
    val onLongClickTimer: () -> Unit,
    val onClickAddTimer: (TimerTime) -> Unit,
    val onClickRemoveTimer: (TimerTime) -> Unit
)

@Composable
internal fun TimerView(
    viewModel: TimerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val uiEvents = remember {
        TimerUiEvents(
            onClickTimer = { viewModel.handleIntent(TimerIntent.ClickTimer(it)) },
            onLongClickTimer = { viewModel.handleIntent(TimerIntent.LongClickTimer) },
            onClickAddTimer = { viewModel.handleIntent(TimerIntent.ClickAddTimer(it)) },
            onClickRemoveTimer = { viewModel.handleIntent(TimerIntent.ClickRemoveTimer(it)) }
        )
    }

    TimerViewImpl(uiState, uiEvents)

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TimerEffect.ShowInvalidTimeToast -> showToast(context, R.string.invalid_time)
            }
        }
    }
}

@Composable
private fun TimerViewImpl(
    uiState: TimerState,
    uiEvents: TimerUiEvents
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        VerticalSpacer(36.dp)
        TimerClock(
            entireTime = uiState.entireTime,
            currentTime = uiState.currentTime,
            onClickTimer = { uiEvents.onClickTimer(uiState.timerStatus) },
            onLongClickTimer = { uiEvents.onLongClickTimer() },
            modifier = Modifier
        )
        VerticalSpacer(36.dp)

        TimeSetSection(
            timerStatus = uiState.timerStatus,
            timerTimes = uiState.timerTimes,
            currentTimerIndex = uiState.currentTimerIndex,
            onClickAddTimer = uiEvents.onClickAddTimer,
            onClickRemoveTimer = uiEvents.onClickRemoveTimer
        )
    }
}

@FullScreenPreview
@Composable
private fun Preview() {
    TimerViewImpl(TimerState(100f, 50f), TimerUiEvents({}, {}, {}, {}))
}

