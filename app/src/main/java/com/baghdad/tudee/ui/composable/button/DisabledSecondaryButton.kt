package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun DisabledSecondaryButton(
    text: String, modifier: Modifier = Modifier,
    color: Brush = Brush.verticalGradient(
        listOf(
           Color.Unspecified, Color.Unspecified
        )
    ),onKlick:()->Unit={},
    isDiabled :Boolean= true,
    isLoading:Boolean=false
) {
    BasicButton(
        borderRadius = 100.dp,
        borderStroke = BorderStroke(1.dp, Theme.color.textColor.stroke),
        onClick =onKlick, backgroundColor = color,
        modifier = modifier,
        isDiabled = isDiabled
        , isLoading = isLoading
    ) {
        Row() {
            BasicText(
                text = text,
                style = Theme.typography.label.large.copy(color = Theme.color.textColor.stroke)

            )
        }

    }
}
