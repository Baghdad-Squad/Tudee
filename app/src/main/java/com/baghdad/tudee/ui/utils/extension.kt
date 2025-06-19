package com.baghdad.tudee.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreenUIState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    this.clickable(
        interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }

fun Modifier.insideBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp = 0.dp
) = this.
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



fun HomeScreenUIState.isEmptyTasks(): Boolean {
    return inProgressTasks.isEmpty() && todoTasks.isEmpty() && doneTasks.isEmpty()
}


fun Modifier.radialGradientBackground(colors: List<Color>) = this.background(
    brush = Brush.radialGradient(
        colors
    )
)


fun LocalDate.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date