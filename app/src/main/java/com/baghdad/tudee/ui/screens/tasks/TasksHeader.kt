package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun TasksHeader(
    monthAndYear: String,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationIcon(
            icon = painterResource(id = R.drawable.ic_left_arrow),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = monthAndYear,
                style = Theme.typography.label.medium,
                color = Theme.color.textColor.body,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "arrow down",
                tint = Theme.color.textColor.body,
                modifier = Modifier.rotate(90f)
            )
        }
        NavigationIcon(
            icon = painterResource(id = R.drawable.ic_right_arrow),
        )
    }
}

@Composable
fun NavigationIcon(
    icon:Painter,
    modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Theme.color.textColor.stroke,
                shape = CircleShape
            )
    ){
        Icon(
            painter = icon,
            contentDescription = "navigation icon",
            tint = Theme.color.textColor.body,
            modifier = Modifier.padding(8.dp)
        )
    }
}