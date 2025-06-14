package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Brush = Brush.linearGradient(
        listOf(
            Theme.color.primaryColor.normal,
            Theme.color.primaryColor.normal
        )
    ),
    borderRadius: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(borderRadius),
    borderStroke: BorderStroke? = null,
    padding: PaddingValues = PaddingValues(12.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = backgroundColor,
                shape = shape
            )
            .let { currentModifier ->
                if (borderStroke != null) {
                    currentModifier.border(
                        border = borderStroke,
                        shape = shape
                    )
                } else {
                    currentModifier
                }.clip(shape)
            }
            .clickable(onClick = onClick)
            .padding(padding),
        contentAlignment = Alignment.Center

    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ExampleUsage() {
    BasicButton(
        onClick = { },
        borderStroke = BorderStroke(2.dp, Color.Black),
        borderRadius = 12.dp
    ) {
        Text(text = "Custom Button")
    }
}