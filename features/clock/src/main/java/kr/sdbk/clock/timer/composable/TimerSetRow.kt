package kr.sdbk.clock.timer.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.sdbk.clock.R
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.design_system.component.image.BaseImage
import kr.sdbk.design_system.component.picker.NumberPicker
import kr.sdbk.design_system.component.spacer.HorizontalSpacer
import kr.sdbk.design_system.component.text.BaseText
import kr.sdbk.design_system.component.text.BasicCenteringText
import kr.sdbk.design_system.modifier.clickable.rippleClickable


@Composable
internal fun TimeSetRow(
    onClickAdd: (TimerTime) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var minute by remember { mutableIntStateOf(0) }
        var second by remember { mutableIntStateOf(0) }

        TimerPicker(
            formatter = { "%02d 분".format(it) },
            modifier = Modifier.weight(1f)
        ) { minute = it }

        BaseText(
            text = ":",
            fontSize = 26.sp
        )

        TimerPicker(
            formatter = { "%02d 초".format(it) },
            modifier = Modifier.weight(1f)
        ) { second = it }
        HorizontalSpacer(24.dp)

        BaseImage(
            resource = R.drawable.ic_timer,
            modifier = Modifier
                .size(36.dp)
                .rippleClickable { onClickAdd(TimerTime(minute, second)) }
        )
        HorizontalSpacer(24.dp)
    }
}

@Composable
private fun TimerPicker(
    formatter: (Int) -> String,
    modifier: Modifier = Modifier,
    onValueChanged: (Int) -> Unit,
) {
    NumberPicker(
        values = (0..59).map { it },
        onValueChanged = onValueChanged,
        formatter = formatter,
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        BasicCenteringText(
            text = it,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.height(36.dp)
        )
    }
}