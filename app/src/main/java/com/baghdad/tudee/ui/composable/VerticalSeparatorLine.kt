package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun VerticalSeparatorLine(
    modifier: Modifier = Modifier,
    height: Dp = 1.dp,
    color: Color = Theme.color.textColor.stroke
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                color = color,
            )
    )
}