package kr.sdbk.design_system.component.picker

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.util.fastMap
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun NumberPicker(
    values: List<Int>,
    onValueChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemCount: Int = 3,
    formatter: (Int) -> String = { it.toString() },
    item: @Composable (String) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = with (Int.MAX_VALUE / 2) { this - (this % values.size) } - (visibleItemCount / 2))
    val flingBehavior = rememberSnapFlingBehavior(listState)

    SubcomposeLayout(Modifier.wrapContentHeight().then(modifier)) { constraints ->
        val itemMeasurables = subcompose("pickerItem", { item("0") })
        val itemPlaceables = itemMeasurables.fastMap { it.measure(constraints) }

        val height = itemPlaceables.maxOf { it.height } * visibleItemCount

        val listMeasurables = subcompose("list") {
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                modifier = Modifier.height(height.toDp())
            ) {
                items(Int.MAX_VALUE) { item(formatter(values[it % values.size])) }
            }
        }

        val listPlaceables = listMeasurables.map { it.measure(constraints) }

        val width = listPlaceables.firstOrNull()?.width ?: 0

        layout(width, height) {
            listPlaceables.forEach { placeable -> placeable.placeRelative(0, 0) }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { isScrolling ->
                val index = listState.firstVisibleItemIndex + 1
                if (!isScrolling) onValueChanged(values[index % values.size])
            }
    }
}