package kr.sdbk.clock

import kr.sdbk.clock.model.ClockType
import kr.sdbk.ui.viewmodel.Effect
import kr.sdbk.ui.viewmodel.Intent
import kr.sdbk.ui.viewmodel.State

data class ClockState(
    val clockType: ClockType = ClockType.Timer
) : State

sealed interface ClockIntent : Intent {
    data class SelectClockType(val clockType: ClockType) : ClockIntent
}

sealed interface ClockEffect : Effect