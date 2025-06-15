package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun ProgressBar(
    currentScreen: Int,
    modifier: Modifier = Modifier,
) {
    val activeColor = Theme.color.primaryColor.normal
    val inactiveColor = Theme.color.textColor.disable
    val screen = listOf(1, 2, 3)

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        screen.forEach() { screen ->
            val color = if (currentScreen == screen) activeColor else inactiveColor

            Box(
                modifier = Modifier
                    .height(5.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(color = color)
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