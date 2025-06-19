package com.baghdad.tudee.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    shape: Shape = RectangleShape,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 6.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidthPx = width.toPx()
        val dashLengthPx = dashLength.toPx()
        val gapLengthPx = gapLength.toPx()

        val paint = Paint().apply {
            this.color = color
            style = PaintingStyle.Stroke
            this.strokeWidth = strokeWidthPx
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLengthPx, gapLengthPx), 0f)
        }

        val outline = shape.createOutline(size, layoutDirection, this)
        when (outline) {
            is Outline.Rounded -> {
                drawRoundRect(
                    topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                    size = Size(size.width - strokeWidthPx, size.height - strokeWidthPx),
                    cornerRadius = CornerRadius(
                        x = outline.roundRect.topLeftCornerRadius.x,
                        y = outline.roundRect.topLeftCornerRadius.y
                    ),
                    style = Stroke(width = strokeWidthPx, pathEffect = paint.pathEffect),
                    color = color
                )

            }

            is Outline.Generic -> {
                drawPath(
                    path = outline.path,
                    color = color,
                    style = Stroke(width = strokeWidthPx, pathEffect = paint.pathEffect)
                )
            }

            is Outline.Rectangle -> {
                drawRect(
                    color = color,
                    topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                    size = Size(size.width - strokeWidthPx, size.height - strokeWidthPx),
                    style = Stroke(width = strokeWidthPx, pathEffect = paint.pathEffect)
                )
            }
        }
    }
)