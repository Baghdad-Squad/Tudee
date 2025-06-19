package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.baghdad.tudee.ui.utils.radialGradientBackground

@Composable
fun RadialGradientCircle(
    modifier: Modifier = Modifier,
    colors : List<Color> = listOf(Color(0xFFCDDBFF),Color(0xFFBFD2FF))
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .radialGradientBackground(
                colors
            )
    )

}

