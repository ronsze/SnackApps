package kr.sdbk.clock.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.sdbk.clock.R

enum class ClockType(@param:StringRes val label: Int, @param:DrawableRes val icon: Int) {
    Timer(R.string.tab_timer, R.drawable.ic_timer),
    Stopwatch(R.string.tab_stopwatch, R.drawable.ic_stopwatch)
}