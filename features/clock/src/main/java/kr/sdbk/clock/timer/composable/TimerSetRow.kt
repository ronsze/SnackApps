package kr.sdbk.clock.timer.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.design_system.component.picker.NumberPicker
import kr.sdbk.design_system.component.spacer.HorizontalSpacer
import kr.sdbk.design_system.component.text.BaseText
import kr.sdbk.design_system.component.text.BasicCenteringText
import kr.sdbk.design_system.ui.theme.white


@Composable
internal fun TimeSetRow(
    onClickAdd: (TimerTime) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var minute by remember { mutableIntStateOf(0) }
        var second by remember { mutableIntStateOf(0) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            TimerPicker(
                formatter = { "%02d 분".format(it) },
                modifier = Modifier.weight(1f)
            ) { minute = it }

            BaseText(
                text = ":",
                fontSize = 48.sp
            )

            TimerPicker(
                formatter = { "%02d 초".format(it) },
                modifier = Modifier.weight(1f)
            ) { second = it }
        }
        HorizontalSpacer(12.dp)

        Button(
            onClick = { onClickAdd(TimerTime(minute, second)) }
        ) {
            BaseText(
                text = "추가",
                color = white,
                fontSize = 16.sp
            )
        }
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
    ) {
        BasicCenteringText(
            text = it,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().height(36.dp)
        )
    }
}

@Preview(widthDp = 375)
@Composable
private fun RowPreview() {
    TimeSetRow{}
}