package kr.sdbk.clock.timer.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import kr.sdbk.design_system.modifier.clickable.noRippleClickable
import kr.sdbk.design_system.preview.WhiteBackgroundPreview
import kr.sdbk.design_system.ui.theme.gray
import kr.sdbk.design_system.ui.theme.yellow

@Composable
internal fun TimerClock(
    entireTime: Float,
    currentTime: Float,
    modifier: Modifier = Modifier,
    onClickTimer: () -> Unit,
    onLongClickTimer: () -> Unit
) {
    Box(
        modifier = modifier
            .noRippleClickable(
                onClick = onClickTimer,
                onLongClick = onLongClickTimer
            )
    ) {
        val canvasModifier = Modifier
            .fillMaxWidth(0.65f)
            .aspectRatio(1f)
        val strokeWidth = 45f

        Canvas(
            modifier = canvasModifier
        ) {
            drawArc(
                color = gray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Canvas(
            modifier = canvasModifier
        ) {
            val sweepAngle = if (entireTime == 0f) 0f else 360f / (entireTime / currentTime)
            drawArc(
                color = yellow,
                startAngle = -90f,
                sweepAngle = -sweepAngle,
                useCenter = false,
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                size = Size(size.width - strokeWidth, size.height - strokeWidth),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}

@WhiteBackgroundPreview
@Composable
private fun TimerClockPreview() {
    TimerClock(100f, 50f, Modifier, {}, {})
}