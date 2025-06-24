package com.baghdad.tudee.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun ProgressBar(
    currentScreen: Int,
    modifier: Modifier = Modifier,
    numOfScreens :Int =3,
) {
    val activeColor = Theme.color.primaryColor.normal
    val inactiveColor = Theme.color.textColor.disable

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (screen in 1..numOfScreens){
            val animatedColor by animateColorAsState(
                targetValue = if (currentScreen == screen) activeColor else inactiveColor
            )
            Box(
                modifier = Modifier
                    .padding(end = if(screen != numOfScreens){ 16.dp } else { 0.dp })
                    .height(5.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(color = animatedColor)
            )
        }
    }
}


@Preview
@Composable
fun ProgressBarPreview() {
    ProgressBar(
        currentScreen = 1
    )
}