package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R

@Composable
fun DisabledFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_black_download,
    color: Brush = Brush.verticalGradient(
        listOf(
            Color(0xFFE8EBED),
            Color(0xFFE8EBED)
        )
    )

) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = {}, backgroundColor = color,
        modifier = modifier.size(64.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
        )
    }
}
