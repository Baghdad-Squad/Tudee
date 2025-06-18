package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme



@Composable
fun DayNightSwitch(isDay: Boolean, onClick: () -> Unit , modifier: Modifier = Modifier) {
    val animateBackgroundColor by animateColorAsState(
        targetValue = if (isDay) Theme.color.primaryColor.normal else Color(0xFF1A1A44),
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .size(height = 36.dp, width = 64.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(width = 1.dp, color = Theme.color.textColor.stroke, shape = RoundedCornerShape(100.dp))
            .background(color = animateBackgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            AnimatedVisibility(
                visible = isDay,
                enter = fadeIn(initialAlpha = 0.0f),
                exit = slideOut(
                    animationSpec = tween(800)
                ) {
                    IntOffset(
                        it.width - 80,
                        0
                    )
                } + fadeOut(animationSpec = tween(800) , targetAlpha = 0.1f)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.sun),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterStart)
                                .clip(CircleShape)
                                .clickable(
                                    onClick = { onClick() }
                                )
                        )
                    }

                }
            }
            AnimatedVisibility(
                visible = !isDay,
                enter = fadeIn(initialAlpha = 0.0f),
                exit = slideOut(
                    animationSpec = tween(800)
                ) { IntOffset(-it.width + 80, 0) } + fadeOut(tween(800),targetAlpha = 0.1f),

                ) {
                Box(Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.moon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterEnd)
                            .clip(CircleShape)
                            .clickable(
                                onClick = { onClick() }
                            )
                    )
                }
            }
        }
        NightStars(isDay)
        CloudCircles(isDay)
        FadedMoonCircles(isDay)
        MoonCircleToCloudCircle(isDay)
    }
}


@Preview
@Composable
private fun DayNightSwitchPreview() {
    TudeeTheme {
        var state = remember { mutableStateOf(true) }

        DayNightSwitch(
            isDay = state.value,
            onClick = { state.value = !state.value }
        )
    }

}