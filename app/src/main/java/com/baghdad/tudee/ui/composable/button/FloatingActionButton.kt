package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_white_loading,
    color: Brush = Theme.color.primaryColor.gradient,
    onClick: () -> Unit = {},
    isLoading: Boolean = true,
    isDisabled: Boolean = false,
    size: Dp = 64.dp,
    borderRadius: Dp = 100.dp, disabledColor: Brush = Brush.verticalGradient(
        listOf(
            Theme.color.textColor.disable,
            Theme.color.textColor.disable
        )
    ), iconColorFilter: ColorFilter = ColorFilter.tint(Theme.color.textColor.onPrimary)
) {
    val iconColor = if (isDisabled) {
        ColorFilter.tint(Theme.color.textColor.stroke)
    } else {
        iconColorFilter
    }
    val backgroundColor = if (isDisabled) disabledColor else color
    BasicButton(
        borderRadius = borderRadius,
        onClick = onClick, backgroundColor = backgroundColor,
        modifier = modifier.size(size), isLoading = isLoading, isDiabled = isDisabled
    ) {
        if (isLoading) {
            Image(
                painter = painterResource(icon), contentDescription = null,
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_white_download),
                contentDescription = null,
                colorFilter =
                    iconColor
            )
        }
    }
}
