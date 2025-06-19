package com.baghdad.tudee.ui.screens.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun CategoryScreenBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Theme.color.surfaceColor.surfaceHigh)
            .padding(16.dp, 20.dp)

    ) {
        Text(
            text = "Categories",
            modifier = Modifier.align(Alignment.CenterStart),
            style = Theme.typography.title.large,
            color = Theme.color.textColor.title
        )
    }

}