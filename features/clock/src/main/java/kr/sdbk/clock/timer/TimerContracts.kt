package kr.sdbk.clock.timer

import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.ui.viewmodel.Effect
import kr.sdbk.ui.viewmodel.Intent
import kr.sdbk.ui.viewmodel.State

data class TimerState(
    val entireTime: Float = 1f,
    val currentTime: Float = 1f,
    val currentTimerIndex: Int = -1,
    val timerTimes: List<TimerTime> = emptyList(),
    val timerStatus: TimerStatus = TimerStatus.IDLE
) : State

sealed interface TimerIntent : Intent {
    data class ClickTimer(val currentStatus: TimerStatus) : TimerIntent
    data object LongClickTimer : TimerIntent
    data class ClickAddTimer(val timerTime: TimerTime) : TimerIntent
    data class ClickRemoveTimer(val timerTime: TimerTime) : TimerIntent
}

sealed interface TimerEffect : Effect {
    data object ShowInvalidTimeToast : TimerEffect
}