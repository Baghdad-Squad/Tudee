package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun DisabledSecondaryButton(
    text: String, modifier: Modifier = Modifier,
    color: Brush = Brush.verticalGradient(
        listOf(
            Theme.color.textColor.disable,
            Theme.color.textColor.disable
        )
    )
) {
    BasicButton(
        borderRadius = 100.dp,
        borderStroke = BorderStroke(1.dp, Theme.color.textColor.stroke),
        onClick = {}, backgroundColor = color,
        modifier = modifier

    ) {
        Row() {

            BasicText(
                text = text,
                style = Theme.typography.label.large.copy(color = Theme.color.textColor.stroke)

            )

        }

    }
}
