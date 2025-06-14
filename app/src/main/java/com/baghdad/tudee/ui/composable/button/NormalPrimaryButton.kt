package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun NormalPrimaryButton(
    text: String, modifier: Modifier = Modifier,
    color:Brush =Brush.verticalGradient(listOf(Theme.color.primaryColor.normal,Color(0xFF3A9CCD)))
) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = {}, backgroundColor = color,
        modifier = modifier

        ) {
        BasicText(
            text = text,
            style = Theme.typography.label.large.copy(color= Theme.color.textColor.onPrimary)

        )
    }
}


@Preview
@Composable
fun peveiwforbutton() {

    TudeeTheme { NormalPrimaryButton("Submit") }
}