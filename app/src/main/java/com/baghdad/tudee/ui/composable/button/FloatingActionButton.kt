package com.baghdad.tudee.ui.composable.button

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
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
    isError: Boolean = false,
    isEnabled: Boolean = true,
) {
    val backgroundColor = when {
        isEnabled.not() -> Theme.color.textColor.disable
        isError -> Theme.color.status.errorVariant
        else -> Theme.color.primaryColor.normal
    }
    val contentColor = when {
        isEnabled.not() -> Theme.color.textColor.stroke
        isError -> Theme.color.status.error
        else -> Theme.color.textColor.onPrimary

    }
    BasicButton(
        onClick = onClick,
        modifier = modifier.size(64.dp),
        isEnabled = isEnabled,
        contentPadding = PaddingValues(18.dp),
        colors = ButtonDefaults.colors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
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
                    color = contentColor
                )
            } else {
                Box(
                    modifier =
                        Modifier
                            .size(28.dp)
                            .paint(
                                painter,
                                colorFilter = ColorFilter.tint(
                                    color = contentColor
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