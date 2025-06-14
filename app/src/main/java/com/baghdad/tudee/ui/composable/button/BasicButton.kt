package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    backgroundColor: Color = Theme.color.primaryColor.normal,
    contentColor: Color = Theme.color.primaryColor.normal,
    borderRadius: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(borderRadius),
    borderStroke: BorderStroke? = null,
    height:Dp= 65.dp,
    padding: PaddingValues = PaddingValues(12.dp),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = modifier
                .clickable(onClick = onClick)
                .background(
                    color = backgroundColor,
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
                    }
                }
                .padding(padding).height(height),
            contentAlignment = Alignment.Center


        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExampleUsage() {
    BasicButton(
        onClick = {  },
        backgroundColor = Color(0xFF4A90E2),
        contentColor = Color.Black,
        borderStroke = BorderStroke(2.dp, Color.Black),
        borderRadius = 12.dp
    ) {
        Text(text = "Custom Button")
    }
}