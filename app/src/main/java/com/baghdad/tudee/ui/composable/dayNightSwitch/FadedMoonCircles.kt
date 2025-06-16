package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FadedMoonCircles(isDay: Boolean) {
    AnimatedVisibility(
        visible = !isDay,
        enter = fadeIn(initialAlpha = 1f),
        exit = fadeOut(targetAlpha = 1f)
    ) {
        Box(modifier = Modifier.Companion.fillMaxSize()) {
            Circle(
                Modifier.Companion
                    .size(14.dp)
                    .offset(38.dp, 14.dp)

            )
            Circle(
                Modifier.Companion
                    .size(4.dp)
                    .offset(53.dp, 22.dp)
            )
        }
    }
}