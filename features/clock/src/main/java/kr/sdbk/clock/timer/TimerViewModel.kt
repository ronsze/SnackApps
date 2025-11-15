package kr.sdbk.clock.timer

import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class TimerViewModel @Inject constructor() : BaseViewModel<TimerState, TimerIntent, TimerEffect>(
    initialState = TimerState()
) {
    companion object {
        const val IDLE_TIME = 1
    }

    override fun initializeData() {
        resetTimer()
    }

    override fun handleIntent(intent: TimerIntent) {
        when (intent) {
            is TimerIntent.ClickTimer -> onClickTimer(intent.currentStatus)
            is TimerIntent.LongClickTimer -> stopTimer()
            is TimerIntent.ClickAddTimer -> addTimer(intent.timerTime)
            is TimerIntent.SelectTimer -> updateState { it.copy(currentTimerIndex = intent.index) }
            is TimerIntent.ClickRemoveTimer -> updateState { it.copy(timerTimes = it.timerTimes - intent.timerTime) }
            is TimerIntent.ClickCycleEnable -> updateState { it.copy(isCycleEnabled = !intent.current) }
            is TimerIntent.ClickRepeatEnable -> updateState { it.copy(isRepeatEnabled = !intent.current) }
        }
    }

    private fun onClickTimer(currentStatus: TimerStatus) {
        when (currentStatus) {
            TimerStatus.IDLE -> {
                startTimer()
            }
            TimerStatus.PAUSED -> {
                resumeTimer()
            }
            TimerStatus.RUNNING -> {
                pauseTimer()
            }
        }
    }

    private fun addTimer(timerTime: TimerTime) {
        if (timerTime.getTotalMilliSeconds() <= 0) sendEffect(TimerEffect.ShowInvalidTimeToast)
        else updateState { it.copy(timerTimes = it.timerTimes + timerTime) }
    }

    private fun startTimer() {
        updateState { it.copy(timerStatus = TimerStatus.RUNNING) }
    }

    private fun pauseTimer() {
        updateState { it.copy(timerStatus = TimerStatus.PAUSED) }
    }

    private fun resumeTimer() {
    }

    private fun stopTimer() {
        updateState { it.copy(timerStatus = TimerStatus.IDLE) }
    }

    private fun resetTimer() {
        val timerIndex = if (state.value.isCycleEnabled) 0 else state.value.currentTimerIndex
        updateState { it.copy(entireTimeMs = IDLE_TIME, currentTimeMs= IDLE_TIME, currentTimerIndex = timerIndex) }
    }

    private fun TimerTime.getTotalMilliSeconds() = (minute * 60 + second) * 1000
}