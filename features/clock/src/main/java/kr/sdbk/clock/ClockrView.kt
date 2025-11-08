package kr.sdbk.clock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.sdbk.clock.model.ClockType
import kr.sdbk.clock.stopwatch.StopwatchView
import kr.sdbk.clock.timer.TimerView
import kr.sdbk.design_system.component.image.BaseImage
import kr.sdbk.design_system.component.text.BaseText

internal data class ClockEvents(
    val popBackStack: () -> Unit
)

internal data class ClockUiEvents(
    val onBackPressed: () -> Unit,
    val selectType: (ClockType) -> Unit
)

@Composable
internal fun ClockView(
    events: ClockEvents,
    viewModel: ClockViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val uiEvents = remember {
        ClockUiEvents(
            onBackPressed = events.popBackStack,
            selectType = { viewModel.handleIntent(ClockIntent.SelectClockType(it)) }
        )
    }

    ClockViewImpl(uiState, uiEvents)
}

@Composable
private fun ClockViewImpl(
    uiState: ClockState,
    uiEvents: ClockUiEvents
) {
    Column {
        PrimaryTabRow(
            selectedTabIndex = uiState.clockType.ordinal,
        ) {
            ClockType.entries.forEach {
                ClockTabItem(
                    type = it,
                    isSelected = uiState.clockType == it,
                    onClick = uiEvents.selectType
                )
            }
        }

        Container(uiState.clockType)
    }
}

@Composable
private fun ClockTabItem(
    type: ClockType,
    isSelected: Boolean,
    onClick: (ClockType) -> Unit
) {
    Tab(
        selected = isSelected,
        onClick = { onClick(type) },
        text = {
            BaseText(
                text = stringResource(type.label)
            )
        },
        icon = {
            BaseImage(
                resource = type.icon,
                modifier = Modifier.size(36.dp)
            )
        },
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
private fun Container(
    type: ClockType
) {
    when (type) {
        ClockType.Timer -> {
            TimerView()
        }
        ClockType.Stopwatch -> {
            StopwatchView()
        }
    }
}