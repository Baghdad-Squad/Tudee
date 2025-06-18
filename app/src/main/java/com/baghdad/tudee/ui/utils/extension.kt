package com.baghdad.tudee.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    this.clickable(
        interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }

fun Modifier.insideBorder(
    width: Dp,
    color: androidx.compose.ui.graphics.Color,
    cornerRadius: Dp = 0.dp
) = this.then(
    drawWithContent {
        drawContent()
        val strokeWidth = width.toPx()
        val radius = cornerRadius.toPx()

        drawRoundRect(
            color = color,
            style = Stroke(width = strokeWidth),
            cornerRadius = CornerRadius(radius, radius),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            topLeft = Offset(strokeWidth/2, strokeWidth/2)
        )
    }
)

fun Modifier.radialGradientBackground(colors: List<Color>) = this.background(
    brush = Brush.radialGradient(
        colors
    )
)