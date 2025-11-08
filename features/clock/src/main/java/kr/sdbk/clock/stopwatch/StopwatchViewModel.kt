package kr.sdbk.clock.stopwatch

import kr.sdbk.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class StopwatchViewModel @Inject constructor(

) : BaseViewModel<StopwatchState, StopwatchIntent, StopwatchEffect>(
    initialState = StopwatchState
) {
    override fun handleIntent(intent: StopwatchIntent) {}
}