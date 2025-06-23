package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun TextHeadTaskSection(
    name: String,
    numberOfItem: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            color = Theme.color.textColor.title,
            style = Theme.typography.title.large
        )
        ChipTextWithArrowIcon(numberOfItem) {
            onClick()
        }

    }
}