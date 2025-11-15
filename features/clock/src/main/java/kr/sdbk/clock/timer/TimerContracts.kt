package kr.sdbk.clock.timer

import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.clock.timer.TimerViewModel.Companion.IDLE_TIME
import kr.sdbk.ui.viewmodel.Effect
import kr.sdbk.ui.viewmodel.Intent
import kr.sdbk.ui.viewmodel.State

data class TimerState(
    val entireTimeMs: Int = IDLE_TIME,
    val currentTimeMs: Int = IDLE_TIME,
    val currentTimerIndex: Int = 0,
    val timerTimes: List<TimerTime> = emptyList(),
    val timerStatus: TimerStatus = TimerStatus.IDLE,
    val isRepeatEnabled: Boolean = false,
    val isCycleEnabled: Boolean = false
) : State

sealed interface TimerIntent : Intent {
    data object ClickStart : TimerIntent
    data object ClickResume : TimerIntent
    data object ClickPause : TimerIntent
    data object ClickStop : TimerIntent
    data class ClickAddTimer(val timerTime: TimerTime) : TimerIntent
    data class SelectTimer(val index: Int) : TimerIntent
    data class ClickRemoveTimer(val timerTime: TimerTime) : TimerIntent
    data class ClickCycleEnable(val current: Boolean) : TimerIntent
    data class ClickRepeatEnable(val current: Boolean) : TimerIntent
}

sealed interface TimerEffect : Effect {
    data object ShowInvalidTimeToast : TimerEffect
}