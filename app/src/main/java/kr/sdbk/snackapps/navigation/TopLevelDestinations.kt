package kr.sdbk.snackapps.navigation

import androidx.annotation.DrawableRes
import kr.sdbk.clock.ClockRoute
import kotlin.reflect.KClass

enum class TopLevelDestinations(val label: String, @param:DrawableRes val image: Int, val dest: Any, val destClass: KClass<*>) {
    CLOCK(
        "시계",
        kr.sdbk.clock.R.drawable.ic_clock,
        ClockRoute,
        ClockRoute::class
    )
}