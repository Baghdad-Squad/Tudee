package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun VerticalSeparatorLine(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                color = Theme.color.textColor.stroke,
                shape = CircleShape
            )
            .padding(bottom = 12.dp)
    )
}