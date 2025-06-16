package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun MoonCircleToCloudCircle(isDay: Boolean) {
    val transition = updateTransition(isDay)
    val firstCircleSize = transition.animateDp(transitionSpec = { tween(800) }){ isDay -> if (isDay) 28.dp else 8.dp }
    val secondCircleSize = transition.animateDp(transitionSpec = { tween(800) }) { isDay -> if (isDay) 16.dp else 8.dp }

    val firstColor =
        transition.animateColor(transitionSpec = { tween(300) }) { isDay -> if (isDay) Color(
            0xFFFFFFFF
        ) else Color(0xFFCDDBFF)
        }
    val secondColor =
        transition.animateColor(transitionSpec = { tween(300) }) { isDay -> if (isDay) Color(
            0xFFFFFFFF
        ) else Color(0xFFBFD2FF)
        }
    val thirdColor =
        transition.animateColor(transitionSpec = { tween(300) }) { isDay -> if (isDay) Color(
            0xFFFFFFFF
        ) else Color(0x00BFD2FF)
        }
    val firstCircleOffset =
        transition.animateOffset(transitionSpec = { tween(800) }) { isDay -> if (isDay) Offset(
            47.5f,
            -0f
        ) else Offset(42f, 5f)
        }
    val secondCircleOffset =
        transition.animateOffset(transitionSpec = { tween(800) }) { isDay -> if (isDay) Offset(
            33.5f,
            24f
        ) else Offset(24f, 28f)
        }

    Box(Modifier.Companion.fillMaxSize()) {
        Circle(
            Modifier.Companion
                .size(firstCircleSize.value)
                .offset(firstCircleOffset.value.x.dp, firstCircleOffset.value.y.dp)
                .zIndex(1f),
            firstColor = firstColor.value,
            secondColor = secondColor.value

        )

        Circle(
            Modifier.Companion
                .size(secondCircleSize.value - 2.dp, secondCircleSize.value)
                .offset(
                    secondCircleOffset.value.x.dp,
                    secondCircleOffset.value.y.dp
                )
                .zIndex(1f),
            firstColor = thirdColor.value,
            secondColor = thirdColor.value

        )
    }
}