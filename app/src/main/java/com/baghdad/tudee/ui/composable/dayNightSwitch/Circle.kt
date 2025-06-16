package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Circle(
    modifier: Modifier,
    firstColor: Color = Color(0xFFCDDBFF),
    secondColor: Color = Color(0xFFBFD2FF)
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                brush = Brush.Companion.radialGradient(
                    listOf(
                        firstColor,
                        secondColor
                    ),
                )
            )
    )

}