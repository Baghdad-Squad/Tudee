package com.baghdad.tudee.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    this.clickable(
        interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }


fun Modifier.radialGradientBackground(colors: List<Color>) = this.background(
    brush = Brush.radialGradient(
        colors
    )
)