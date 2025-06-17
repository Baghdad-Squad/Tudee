package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CloudCircles(isDay: Boolean) {
    AnimatedVisibility(
        visible = isDay,
        enter = slideIn(animationSpec = tween(500)) { IntOffset(60, 20) },
        exit = slideOut(
            animationSpec = spring(
                stiffness = Spring.StiffnessMedium,
                visibilityThreshold = IntOffset.VisibilityThreshold
            )
        ) { IntOffset(10, 30) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Circle(
                Modifier
                    .size(31.33.dp, 32.dp)
                    .offset(x = 9.5.dp, y = -1.dp)
                    .align(Alignment.TopEnd),
                Color(0xFFF0F0F0), Color(0xFFF0F0F0)
            )
            Circle(
                Modifier
                    .size(16.dp)
                    .offset(-4.dp, 1.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(1f),
                Color(0xFFFFFFFF), Color(0xFFFFFFFF)
            )
            Circle(
                Modifier
                    .size(24.dp)
                    .offset(29.dp, 20.dp),
                Color(0xFFF0F0F0), Color(0xFFF0F0F0)
            )
        }
    }
}