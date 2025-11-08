package kr.sdbk.clock.timer.composable

import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kr.sdbk.clock.R
import kr.sdbk.clock.model.TimerTime
import kr.sdbk.design_system.component.image.BaseImage
import kr.sdbk.design_system.component.spacer.HorizontalSpacer
import kr.sdbk.design_system.component.text.BaseText
import kr.sdbk.design_system.modifier.clickable.rippleClickable


@Composable
internal fun TimeSetRow(
    onClickAdd: (TimerTime) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(130.dp)
    ) {
        var minute by remember { mutableIntStateOf(0) }
        var second by remember { mutableIntStateOf(0) }

        TimerPicker(
            currentValue = minute,
            formatter = { "$it 분" },
            modifier = Modifier.weight(1f)
        ) { minute = it }

        BaseText(
            text = ":",
            fontSize = 26.sp
        )

        TimerPicker(
            currentValue = second,
            formatter = { "$it 초" },
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
    currentValue: Int,
    formatter: (Int) -> String,
    modifier: Modifier = Modifier,
    onValueChanged: (Int) -> Unit,
) {
    AndroidView(
        factory = { context ->
            val values = (0..59)
            NumberPicker(context).apply {
                displayedValues = values.map { formatter(it) }.toTypedArray()
                minValue = values.first
                maxValue = values.last
                value = minValue

                wrapSelectorWheel = true
                setOnLongPressUpdateInterval(100)
                setOnValueChangedListener { picker, old, new -> onValueChanged(new) }
            }
        },
        update = {
            it.value = currentValue
        },
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 24.dp)
    )
}