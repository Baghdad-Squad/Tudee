package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun DisablePrimaryButton(
    text: String, modifier: Modifier = Modifier,
    color: Brush = Brush.verticalGradient(
        listOf(
            Theme.color.textColor.disable,
            Theme.color.textColor.disable
        )
    ),onKlick:()->Unit={}
    , isDiabled :Boolean= true
    ,isLoading:Boolean=false
) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = onKlick, backgroundColor = color,
        modifier = modifier
        , isDiabled = isDiabled,
        isLoading = isLoading
    ) {
        BasicText(
            text = text,
            style = Theme.typography.label.large.copy(color = Color(0x1F1F1F1F))
        )
    }
}
