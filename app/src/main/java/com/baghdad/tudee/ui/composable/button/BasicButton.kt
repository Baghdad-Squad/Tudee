package com.baghdad.tudee.ui.composable.button

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    shape: Shape = ButtonDefaults.defaultShape,
    colors: ButtonColors = ButtonDefaults.colors(),
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit
) {
    val backgroundModifier = if (colors.backgroundGradient != null) Modifier.background(
        colors.backgroundGradient,
        shape
    ) else Modifier.background(colors.backgroundColor, shape)
    val borderModifier = if (border != null) Modifier.border(border, shape) else Modifier
    Box(
        modifier = modifier
            .clip(shape)
            .then(backgroundModifier)
            .then(borderModifier)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(contentPadding)
        ) {
            content()
        }
    }

}

data class ButtonColors(
    val backgroundColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val backgroundGradient: Brush? = null,
)

object ButtonDefaults {
    val defaultHeight: Dp = 56.dp
    val defaultShape: RoundedCornerShape = CircleShape
    val defaultPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    val defaultColorAnimationSpec = tween<Color>(200)

    @Composable
    fun colors(
        backgroundColor: Color = Theme.color.primaryColor.normal,
        contentColor: Color = Theme.color.textColor.onPrimary,
    ) = ButtonColors(backgroundColor, contentColor)
}