package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun NormalPrimaryButton(
    text: String, modifier: Modifier = Modifier,
    color: Brush = Theme.color.primaryColor.gradient
    ,onKlick:()->Unit={}
    ,isLoading:Boolean=false
    , isDiabled :Boolean= false
) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = onKlick, backgroundColor = color,
        modifier = modifier
        , isLoading = isLoading
        , isDiabled = isDiabled
    ) {
        BasicText(
            text = text,
            style = Theme.typography.label.large.copy(color = Theme.color.textColor.onPrimary)
        )
    }
}


@Preview
@Composable
fun peveiwforbutton() {

    TudeeTheme { NormalPrimaryButton("Submit") }
}