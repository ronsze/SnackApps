package kr.sdbk.clock.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.sdbk.clock.R
import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.clock.timer.composable.TimeSetRow
import kr.sdbk.clock.timer.composable.TimerClock
import kr.sdbk.clock.timer.composable.TimerList
import kr.sdbk.design_system.component.spacer.VerticalSpacer
import kr.sdbk.design_system.component.spacer.WeightSpacer
import kr.sdbk.design_system.component.text.BaseText
import kr.sdbk.design_system.preview.FullScreenPreview
import kr.sdbk.platform_extension.functions.showToast

data class TimerUiEvents(
    val onClickStart: () -> Unit,
    val onClickResume: () -> Unit,
    val onClickPause: () -> Unit,
    val onClickStop: () -> Unit,
    val onClickAddTimer: (TimerTime) -> Unit,
    val onSelectTimer: (index: Int) -> Unit,
    val onClickRemoveTimer: (TimerTime) -> Unit,
    val onClickCycleEnable: (current: Boolean) -> Unit,
    val onClickRepeatEnable: (current: Boolean) -> Unit,
)

@Composable
internal fun TimerView(
    viewModel: TimerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val uiEvents = remember {
        TimerUiEvents(
            onClickStart = { viewModel.handleIntent(TimerIntent.ClickStart) },
            onClickResume = { viewModel.handleIntent(TimerIntent.ClickResume) },
            onClickPause = { viewModel.handleIntent(TimerIntent.ClickPause) },
            onClickStop = { viewModel.handleIntent(TimerIntent.ClickStop) },
            onClickAddTimer = { viewModel.handleIntent(TimerIntent.ClickAddTimer(it)) },
            onSelectTimer = { viewModel.handleIntent(TimerIntent.SelectTimer(it)) },
            onClickRemoveTimer = { viewModel.handleIntent(TimerIntent.ClickRemoveTimer(it)) },
            onClickCycleEnable = { viewModel.handleIntent(TimerIntent.ClickCycleEnable(it)) },
            onClickRepeatEnable = { viewModel.handleIntent(TimerIntent.ClickRepeatEnable(it)) },
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
            timerStatus = uiState.timerStatus,
            entireTimeMs = uiState.entireTimeMs,
            currentTimeMs = uiState.currentTimeMs,
            onClickStart = uiEvents.onClickStart,
            onClickResume = uiEvents.onClickResume,
            onClickPause = uiEvents.onClickPause,
            onClickStop = uiEvents.onClickStart
        )
        VerticalSpacer(36.dp)

        if (uiState.timerStatus == TimerStatus.IDLE) TimeSetRow(uiEvents.onClickAddTimer)
        VerticalSpacer(24.dp)

        TimerList(
            timerStatus = uiState.timerStatus,
            timerTimes = uiState.timerTimes,
            currentTimerIndex = uiState.currentTimerIndex,
            onSelectTimer = uiEvents.onSelectTimer,
            onClickRemove = uiEvents.onClickRemoveTimer
        )
        VerticalSpacer(24.dp)
        WeightSpacer(1f)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CheckboxRow(
                text = "모든 타이머 실행",
                isChecked = uiState.isCycleEnabled,
                onChecked = uiEvents.onClickCycleEnable,
                modifier = Modifier.weight(1f)
            )

            CheckboxRow(
                text = "반복 모드",
                isChecked = uiState.isRepeatEnabled,
                onChecked = uiEvents.onClickRepeatEnable,
                modifier = Modifier.weight(1f)
            )
        }
        VerticalSpacer(12.dp)
    }
}

@Composable
private fun CheckboxRow(
    text: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onChecked: (current: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        WeightSpacer(1f)
        BaseText(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Checkbox(
            checked = isChecked,
            onCheckedChange = { onChecked(it) },
            modifier = Modifier.size(36.dp)
        )
        WeightSpacer(1f)
    }
}

@FullScreenPreview
@Composable
private fun Preview() {
    TimerViewImpl(
        uiState = TimerState(100, 50),
        uiEvents = TimerUiEvents({}, {}, {}, {}, {}, {}, {}, {}, {})
    )
}

