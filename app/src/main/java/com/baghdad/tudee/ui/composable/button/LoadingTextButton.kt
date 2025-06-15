package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun LoadingTextButton(
    text: String, modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_blue_loading,
    color: Brush = Brush.verticalGradient(
        listOf(
            Color.Unspecified,
            Color.Unspecified
        )
    ),
) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = {}, backgroundColor = color,
        modifier = modifier

    ) {
        Row() {
            BasicText(
                text = text,
                style = Theme.typography.label.large.copy(color = Theme.color.primaryColor.normal)
            )
            Spacer(Modifier.width(10.dp))
            Image(
                painter = painterResource(icon), contentDescription = null,

                )
        }

    }
}