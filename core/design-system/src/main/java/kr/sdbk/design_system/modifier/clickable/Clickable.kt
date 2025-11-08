package kr.sdbk.design_system.modifier.clickable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.rippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.noRippleClickable(onClick: (() -> Unit)? = null, onLongClick: (() -> Unit)? = null): Modifier = composed {
    if (onClick == null) {
        this
    } else {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
        this.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
    }
}