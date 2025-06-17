package com.baghdad.tudee.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun OnboardingBackground(
    content: @Composable () -> Unit = {},
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val ellipsisColor = Theme.color.surfaceColor.ellipsisGradientColor

    Box {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Theme.color.surfaceColor.surface)
                .background(color = Theme.color.status.overlay)
        ) {
            Box(
                modifier = Modifier
                    .height(900.dp)
                    .width(70.dp)
                    .offset(
                        x = (0.8 * screenWidth).dp,
                        y = -(0.53 * screenHeight).dp
                    )
                    .rotate(-314f)
                    .background(brush = ellipsisColor)
            )
            Box(
                modifier = Modifier
                    .height(600.dp)
                    .width(160.dp)
                    .offset(
                        x = (0.8 * screenWidth).dp,
                        y = -(0.15 * screenHeight).dp
                    )
                    .rotate(-314f)
                    .background(brush = ellipsisColor)
            )
            Box(
                modifier = Modifier
                    .height(600.dp)
                    .width(70.dp)
                    .offset(
                        x = (1.16 * screenWidth).dp,
                        y = -(0.014 * screenHeight).dp
                    )
                    .rotate(-314f)
                    .background(brush = ellipsisColor)
            )
        }
    }
}


@Preview()
@Composable
fun OnboardingBackgroundPreview() {
    TudeeTheme {
        OnboardingBackground()
    }
}