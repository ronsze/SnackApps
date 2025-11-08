package kr.sdbk.clock.timer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class TimerViewModel @Inject constructor() : BaseViewModel<TimerState, TimerIntent, TimerEffect>(
    initialState = TimerState()
) {
    private var timerJob: Job? = null

    override fun initializeData() {
        resetTimer()
    }

    override fun handleIntent(intent: TimerIntent) {
        when (intent) {
            is TimerIntent.ClickTimer -> {
                when (intent.currentStatus) {
                    TimerStatus.IDLE,
                    TimerStatus.PAUSED -> startTimer()
                    TimerStatus.RUNNING -> pauseTimer()
                }
            }
            is TimerIntent.LongClickTimer -> {
                stopTimer()
            }
            is TimerIntent.ClickAddTimer -> {
                addTimer(intent.timerTime)
            }
            is TimerIntent.ClickRemoveTimer -> {
                updateState { it.copy(timerTimes = it.timerTimes - intent.timerTime) }
            }
        }
    }

    private fun addTimer(timerTime: TimerTime) {
        if (timerTime.getTotalSeconds() <= 0) {
            sendEffect(TimerEffect.ShowInvalidTimeToast)
        } else {
            updateState { it.copy(timerTimes = it.timerTimes + timerTime) }
        }
    }

    private fun startTimer() {
        updateState { it.copy(timerStatus = TimerStatus.RUNNING) }

        timerJob = viewModelScope.launch {
            state.value.timerTimes.forEachIndexed { i, time ->
                val entireSeconds = time.getTotalSeconds()

                updateState {
                    it.copy(
                        entireTime = entireSeconds.toFloat(),
                        currentTime = entireSeconds.toFloat(),
                        currentTimerIndex = i
                    )
                }

                while (state.value.currentTime > 0f) {
                    delay(10L)
                    updateState { it.copy(currentTime = it.currentTime - 0.01f) }
                }
            }
            stopTimer()
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        updateState { it.copy(timerStatus = TimerStatus.PAUSED) }
    }

    private fun stopTimer() {
        pauseTimer()
        resetTimer()
        timerJob = null
        updateState { it.copy(timerStatus = TimerStatus.IDLE) }
    }

    private fun resetTimer() {
        updateState { it.copy(entireTime = 1f, currentTime = 1f, currentTimerIndex = -1) }
    }

    private fun TimerTime.getTotalSeconds() = minute * 60 + second
}