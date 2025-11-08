package kr.sdbk.clock.stopwatch

import kr.sdbk.ui.viewmodel.Effect
import kr.sdbk.ui.viewmodel.Intent
import kr.sdbk.ui.viewmodel.State

data object StopwatchState : State
sealed interface StopwatchIntent : Intent
sealed interface StopwatchEffect : Effect