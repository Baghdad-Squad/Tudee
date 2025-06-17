package com.baghdad.tudee.ui.composable.button

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.StripedCircularProgressIndicator

@Composable
fun FloatingActionButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
) {
    val transition = updateTransition(
        targetState = isEnabled,
        label = "fab_state"
    )

    val animatedBackgroundColor by transition.animateColor(
        label = "fab_background_color",
        transitionSpec = { ButtonDefaults.defaultColorAnimationSpec }
    ) { enabled ->
        if (enabled) Theme.color.primaryColor.normal
        else Theme.color.textColor.disable
    }

    val animatedContentColor by transition.animateColor(
        label = "fab_background_color",
        transitionSpec = { ButtonDefaults.defaultColorAnimationSpec }
    ) { enabled ->
        if (enabled) Theme.color.textColor.onPrimary
        else Theme.color.textColor.stroke
    }
    BasicButton(
        onClick = onClick,
        modifier = modifier.size(64.dp),
        isEnabled = isEnabled,
        contentPadding = PaddingValues(18.dp),
        colors = ButtonDefaults.colors(
            backgroundColor = animatedBackgroundColor,
            contentColor = animatedContentColor
        ),
        shape = CircleShape,
    ) {
        Crossfade(
            targetState = isLoading
        ) { state ->
            if (state) {
                StripedCircularProgressIndicator(
                    size = 18.dp,
                    lineLength = 4.dp,
                    lineWidth = 3.dp,
                    color = animatedBackgroundColor
                )
            } else {
                Box(
                    modifier =
                        Modifier
                            .size(28.dp)
                            .paint(
                                painter,
                                colorFilter = ColorFilter.tint(
                                    color = animatedContentColor
                                )
                            )
                )
            }
        }
    }
}


@Preview(name = "Not Loading", showBackground = true)
@Composable
private fun FloatingActionButtonPreview1() {
    TudeeTheme {
        FloatingActionButton(
            onClick = { },
            painter = painterResource(R.drawable.ic_blue_note)
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun FloatingActionButtonPreview2() {
    TudeeTheme {
        FloatingActionButton(
            onClick = { },
            isEnabled = false,
            painter = painterResource(R.drawable.ic_blue_note)
        )
    }
}