package kr.sdbk.clock

import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kr.sdbk.ui.viewmodel.BaseViewModel

@HiltViewModel
internal class ClockViewModel @Inject constructor(

) : BaseViewModel<ClockState, ClockIntent, ClockEffect>(
    initialState = ClockState()
) {
    override fun handleIntent(intent: ClockIntent) {
        when (intent) {
            is ClockIntent.SelectClockType -> updateState { it.copy(clockType = intent.clockType) }
        }
    }
}