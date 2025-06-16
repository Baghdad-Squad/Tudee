package com.baghdad.tudee.ui.composable.dayNightSwitch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R


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
        Box(Modifier.Companion.fillMaxWidth()) {
            stars.forEach { star ->
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(star.size)
                        .offset(star.offset.x.dp, star.offset.y.dp)
                )
            }
        }
    }
}