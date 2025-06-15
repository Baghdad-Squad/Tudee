package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun LoadingPrimaryButton(
    text: String, modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_white_loading,
    color: Brush = Theme.color.primaryColor.gradient
) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = {}, backgroundColor = color,
        modifier = modifier
    ) {
        Row() {
            BasicText(
                text = text,
                style = Theme.typography.label.large.copy(color = Theme.color.textColor.onPrimary)
            )
            Spacer(Modifier.width(10.dp))
            Image(
                painter = painterResource(icon), contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Theme.color.textColor.onPrimary
                )
            )
        }
    }
}
