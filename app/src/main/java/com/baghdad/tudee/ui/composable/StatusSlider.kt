package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun StatusSlider(
    icon: Painter,
    modifier: Modifier = Modifier) {
    Image(
        painter = icon,
        contentDescription = null,
        modifier = modifier.size(20.dp)
    )
}