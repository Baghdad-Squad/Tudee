package com.baghdad.tudee.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun Test() {
    var state = remember { mutableStateOf(true) }

    DayNightSwitch(
        isDay = state.value,
        onClick = { state.value = !state.value }
    )
}

@Composable
fun DayNightSwitch(isDay: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(height = 36.dp, width = 64.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(width = 1.dp, color = Color(0x1F1F1F1F), shape = RoundedCornerShape(100.dp))
            .background(color = if (isDay) Theme.color.primaryColor.normal else Color(0xFF1A1A44))


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
                    animationSpec =
                        spring(
                            stiffness = Spring.StiffnessLow,
                            visibilityThreshold = IntOffset(1, 1)
                        )
                ) {
                    IntOffset(
                        it.width - 80,
                        0
                    )
                } + fadeOut(targetAlpha = 0.1f)
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
                    animationSpec =
                        spring(
                            stiffness = Spring.StiffnessLow,
                            visibilityThreshold = IntOffset(1, 1)
                        )
                ) { IntOffset(-it.width + 79, 0) } + fadeOut(targetAlpha = 0.1f),

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
            NightStars(isDay)
            MoonCircle(isDay)

        }
        CloudCircles(isDay)
    }
}

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
                brush = Brush.radialGradient(
                    listOf(
                        firstColor,
                        secondColor
                    ),
                )
            )
    )

}

@Composable
fun CloudCircles(isDay: Boolean) {
    if(isDay){

        Box(modifier = Modifier.fillMaxSize()) {
            Circle(
                Modifier
                    .size(29.dp)
                    .offset(x = 11.5.dp)
                    .align(Alignment.TopEnd)
                    .zIndex(1f),
                Color(0xFFFFFFFF), Color(0xFFFFFFFF)
            )
            Circle(
                Modifier
                    .size(31.33.dp, 32.dp)
                    .offset(x = 9.5.dp , y = -1.dp)
                    .align(Alignment.TopEnd),
                Color(0xFFF0F0F0), Color(0xFFF0F0F0)
            )
            Circle(
                Modifier
                    .size(16.dp)
                    .offset(-4.dp , 1.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(1f),
                Color(0xFFFFFFFF), Color(0xFFFFFFFF)
            )
            Circle(
                Modifier
                    .size(14.dp, 16.dp)
                    .offset(33.dp, 24.dp)
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

@Composable
fun MoonCircle(isDay: Boolean) {

    FadedMoonCircles(isDay)
    AnimatedVisibility(
        visible = !isDay,
        enter = slideIn() { IntOffset(0, 0) },
        exit = slideOut() { IntOffset(+30, 0) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Circle(
                Modifier
                    .size(8.dp)
                    .offset(39.dp, 3.5.dp)
            )
        }
    }

    AnimatedVisibility(
        visible = !isDay,
        enter = slideIn() { IntOffset(0, 0) },
        exit = slideOut() { IntOffset(+90, -30) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Circle(
                if (!isDay) Modifier.offset(100.dp, 100.dp) else
                    Modifier
                        .size(8.dp)
                        .align(Alignment.BottomCenter)
            )
        }
    }
}


@Composable
fun FadedMoonCircles(isDay: Boolean) {


    AnimatedVisibility(
        visible = !isDay,
        enter = fadeIn(initialAlpha = 1f),
        exit = fadeOut(targetAlpha = 0.0f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Circle(
                Modifier
                    .size(14.dp)
                    .offset(36.dp, 12.dp)

            )
            Circle(
                Modifier
                    .size(4.dp)
                    .offset(49.dp, 22.dp)
            )
        }
    }
}

data class Star(val size: DpSize, val offset: Offset)

@Composable
fun NightStars(isDay: Boolean) {
    val stars = remember {
        listOf(
            Star(DpSize(1.08.dp, 1.7.dp), Offset(5.94f, 14.06f)),
            Star(DpSize(1.08.dp, 1.7.dp), Offset(17.37f, 5.06f)),
            Star(DpSize(1.79.dp, 2.83.dp), Offset(8.69f, 7.87f)),
            Star(DpSize(1.37.dp, 2.16.dp), Offset(28.8f, 15.19f)),
            Star(DpSize(0.91.dp, 1.44.dp), Offset(14.17f, 22.5f)),
            Star(DpSize(0.91.dp, 1.44.dp), Offset(8.23f, 28.39f)),
            Star(DpSize(0.91.dp, 1.69.dp), Offset(23.77f, 30.37f)),
        )
    }

    AnimatedVisibility(
        visible = !isDay,
        enter = fadeIn(initialAlpha = 1f),
        exit = fadeOut(targetAlpha = 0f)
    ) {
        Box(Modifier.fillMaxWidth()) {
            stars.forEach { star ->
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = null,
                    modifier = Modifier
                        .size(star.size)
                        .offset(star.offset.x.dp, star.offset.y.dp)
                )
            }
        }
    }
}