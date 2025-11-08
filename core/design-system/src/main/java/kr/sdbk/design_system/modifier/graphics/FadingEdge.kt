package kr.sdbk.design_system.modifier.graphics

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import kr.sdbk.design_system.ui.theme.black
import kr.sdbk.design_system.ui.theme.transparent

fun Modifier.verticalFadingEdge(edgeValue: Dp) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()

        val edgePx = edgeValue.toPx()
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(transparent, black),
                startY = 0f,
                endY = edgePx,
            ),
            blendMode = BlendMode.DstIn,
        )

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(transparent, black),
                startY = size.height,
                endY = size.height - edgePx,
            ),
            blendMode = BlendMode.DstIn,
        )
    }