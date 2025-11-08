package kr.sdbk.clock.timer.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.design_system.component.spacer.VerticalSpacer

@Composable
internal fun TimeSetSection(
    timerStatus: TimerStatus,
    timerTimes: List<TimerTime>,
    currentTimerIndex: Int,
    onClickAddTimer: (TimerTime) -> Unit,
    onClickRemoveTimer: (TimerTime) -> Unit
) {
    if (timerStatus == TimerStatus.IDLE) {
        TimeSetRow(onClickAddTimer)
    }
    VerticalSpacer(24.dp)

    TimerList(
        timerStatus = timerStatus,
        timerTimes = timerTimes,
        currentTimerIndex = currentTimerIndex,
        onClickRemove = onClickRemoveTimer
    )
    VerticalSpacer(24.dp)
}