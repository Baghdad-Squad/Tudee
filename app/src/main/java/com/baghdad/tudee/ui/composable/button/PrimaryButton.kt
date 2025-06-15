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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_white_loading,
    color: Brush = Theme.color.primaryColor.gradient,
    onClick: () -> Unit = {},
    isLoading: Boolean = true,
    isDisabled: Boolean = false,
    size: Dp = 64.dp,
    borderRadius: Dp = 100.dp,
    disabledColorBackgroundColor: Brush = Brush.verticalGradient(
        listOf(
            Theme.color.textColor.disable,
            Theme.color.textColor.disable
        )
    ),
    disabledColorTextColor: TextStyle =Theme.typography.label.large.copy(Theme.color.textColor.stroke)
) {
    val backgroundColor = if (isDisabled) disabledColorBackgroundColor else color
    val textColor = if (isDisabled) disabledColorTextColor else  Theme.typography.label.large.copy(color = Theme.color.textColor.onPrimary)

    BasicButton(
        borderRadius = borderRadius,
        onClick = onClick, backgroundColor = backgroundColor,
        modifier = modifier, isLoading = isLoading,
        isDiabled = isDisabled
    ) {
        Row() {
            BasicText(
                text = text,
                style = textColor
            )
            if (isLoading) {
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
}
