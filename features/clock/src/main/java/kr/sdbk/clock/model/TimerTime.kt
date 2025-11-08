package kr.sdbk.clock.model

data class TimerTime(
    val minute: Int,
    val second: Int
) {
    companion object {
        val placeholder = TimerTime(1, 0)
    }
}