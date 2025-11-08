package kr.sdbk.clock.timer.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.sdbk.clock.R
import kr.sdbk.clock.model.TimerStatus
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.design_system.component.image.BaseImage
import kr.sdbk.design_system.component.spacer.HorizontalSpacer
import kr.sdbk.design_system.component.text.BasicCenteringText
import kr.sdbk.design_system.modifier.clickable.rippleClickable
import kr.sdbk.design_system.ui.theme.black
import kr.sdbk.design_system.ui.theme.gray
import java.util.Locale

@Composable
internal fun TimerList(
    timerStatus: TimerStatus,
    timerTimes: List<TimerTime>,
    currentTimerIndex: Int,
    onClickRemove: (TimerTime) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(timerTimes) { i, v ->
            TimerListItem(i, v, timerStatus, currentTimerIndex, onClickRemove)
        }
    }
}

@Composable
private fun TimerListItem(
    index: Int,
    timerTime: TimerTime,
    timerStatus: TimerStatus,
    currentTimerIndex: Int,
    onClickRemove: (TimerTime) -> Unit
) {
    val textProperties = when {
        index < currentTimerIndex -> ItemTextProperties(18, gray, FontWeight.Normal)
        index == currentTimerIndex -> ItemTextProperties(22, black, FontWeight.Bold)
        else ->ItemTextProperties(18, black, FontWeight.Normal)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(45.dp)
    ) {
        ItemText(timerTime.minute, textProperties, Modifier.weight(1f)) {
            "${"%02d".format(it)} 분"
        }
        HorizontalSpacer(12.dp)

        ItemText(timerTime.second, textProperties, Modifier.weight(1f)) {
            "${"%02d".format(it)} 초"
        }
        HorizontalSpacer(12.dp)

        if (timerStatus == TimerStatus.IDLE) {
            BaseImage(
                resource = R.drawable.ic_stopwatch,
                modifier = Modifier
                    .size(24.dp)
                    .rippleClickable { onClickRemove(timerTime) }
            )
            HorizontalSpacer(36.dp)
        }
    }
}

@Composable
private fun ItemText(
    value: Int,
    properties: ItemTextProperties,
    modifier: Modifier,
    formatter: (Int) -> String
) {
    BasicCenteringText(
        text = formatter(value),
        color = properties.fontColor,
        fontSize = properties.fontSize.sp,
        fontWeight = properties.fontWeight,
        modifier = modifier
            .fillMaxHeight()
    )
}

private data class ItemTextProperties(
    val fontSize: Int,
    val fontColor: Color,
    val fontWeight: FontWeight,
)

@Preview
@Composable
private fun ItemPreviewPrevious() {
    TimerListItem(0, TimerTime(10, 10), TimerStatus.IDLE, 1) {}
}

@Preview
@Composable
private fun ItemPreviewCurrent() {
    TimerListItem(1, TimerTime(10, 10), TimerStatus.IDLE, 1) {}
}

@Preview
@Composable
private fun ItemPreviewNext() {
    TimerListItem(2, TimerTime(10, 10), TimerStatus.IDLE, 1) {}
}

@Preview
@Composable
private fun ItemPreviewRunning() {
    TimerListItem(1, TimerTime(10, 10), TimerStatus.RUNNING, 1) {}
}