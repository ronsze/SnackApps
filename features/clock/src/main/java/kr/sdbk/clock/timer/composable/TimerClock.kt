package kr.sdbk.clock.timer.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.design_system.component.image.BaseImage
import kr.sdbk.design_system.component.spacer.WeightSpacer
import kr.sdbk.design_system.component.text.BaseText
import kr.sdbk.design_system.function.toDp
import kr.sdbk.design_system.modifier.clickable.rippleClickable
import kr.sdbk.design_system.preview.FullWidthPreview
import kr.sdbk.design_system.preview.WhiteBackgroundPreview
import kr.sdbk.design_system.ui.theme.Purple40
import kr.sdbk.design_system.ui.theme.lightGray
import kotlin.time.ExperimentalTime

private val strokeWidth = 45f

@Composable
internal fun TimerClock(
    timerStatus: TimerStatus,
    entireTimeMs: Int,
    currentTimeMs: Int,
    onClickStart: () -> Unit,
    onClickResume: () -> Unit,
    onClickPause: () -> Unit,
    onClickStop: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            WeightSpacer(1f)
            TimerText(
                currentTimeMs = currentTimeMs,
                modifier = Modifier
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = strokeWidth.toDp())
            ) {
                TimerActionRow(
                    timerStatus = timerStatus,
                    onClickStart = onClickStart,
                    onClickResume = onClickResume,
                    onClickPause = onClickPause,
                    onClickStop = onClickStart
                )
            }
        }

        TimerArc(entireTimeMs, currentTimeMs)
    }
}

@Composable
private fun TimerActionRow(
    timerStatus: TimerStatus,
    onClickStart: () -> Unit,
    onClickResume: () -> Unit,
    onClickPause: () -> Unit,
    onClickStop: () -> Unit
) {
    val (leftButtonIcon, leftButtonEvent) = when (timerStatus) {
        TimerStatus.IDLE -> Icons.Filled.PlayArrow to onClickStart
        TimerStatus.RUNNING -> Icons.Filled.Pause to onClickPause
        TimerStatus.PAUSED -> Icons.Filled.PlayArrow to onClickResume
    }

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        ActionIcon(
            icon = leftButtonIcon,
            onClick = leftButtonEvent
        )

        if (timerStatus != TimerStatus.IDLE) {
            ActionIcon(
                icon = Icons.Filled.Stop,
                onClick = onClickStop
            )
        }
    }
}

@Composable
private fun RowScope.ActionIcon(
    icon: ImageVector,
    onClick: () -> Unit
) {
    BaseImage(
        imageVector = icon,
        modifier = Modifier
            .size(48.dp)
            .rippleClickable(onClick)
    )
}

@Composable
private fun TimerArc(
    entireTimeMs: Int,
    currentTimeMs: Int
) {
    val canvasModifier = Modifier.fillMaxSize()
    Canvas(modifier = canvasModifier) {
        drawArc(
            color = lightGray,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }

    Canvas(modifier = canvasModifier) {
        val sweepAngle = if (entireTimeMs == 0) 0f else 360f / (entireTimeMs / currentTimeMs)
        drawArc(
            color = Purple40,
            startAngle = -90f,
            sweepAngle = -sweepAngle,
            useCenter = false,
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun TimerText(
    currentTimeMs: Int,
    modifier: Modifier = Modifier
) {
    BaseText(
        text = "10:50.00",
        fontSize = 60.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier
    )
}

@FullWidthPreview
@Composable
private fun TimerClockPreview() {
    TimerClock(TimerStatus.IDLE, 100, 50, {}, {}, {}, {})
}

@WhiteBackgroundPreview
@Composable
private fun ActionRowPreviewIdle() {
    TimerActionRow(TimerStatus.IDLE, {}, {}, {}, {})
}

@WhiteBackgroundPreview
@Composable
private fun ActionRowPreviewRunning() {
    TimerActionRow(TimerStatus.RUNNING, {}, {}, {}, {})
}

@WhiteBackgroundPreview
@Composable
private fun ActionRowPreviewPaused() {
    TimerActionRow(TimerStatus.PAUSED, {}, {}, {}, {})
}