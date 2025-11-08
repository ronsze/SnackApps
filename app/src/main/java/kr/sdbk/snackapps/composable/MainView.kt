package kr.sdbk.snackapps.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.sdbk.design_system.component.image.BaseImage
import kr.sdbk.design_system.component.text.BaseText
import kr.sdbk.design_system.modifier.clickable.rippleClickable
import kr.sdbk.snackapps.navigation.TopLevelDestinations

@Composable
fun MainView(
    navigateToFeature: (TopLevelDestinations) -> Unit
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(TopLevelDestinations.entries) {
            GridItem(it, navigateToFeature)
        }
    }
}

@Composable
private fun GridItem(
    item: TopLevelDestinations,
    onClick: (TopLevelDestinations) -> Unit
) {
    Box(
        modifier = Modifier
            .rippleClickable { onClick(item) }
    ) {
        BaseImage(
            resource = item.image,
            Modifier.aspectRatio(1f)
        )

        BaseText(
            text = item.label,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}