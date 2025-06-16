package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.domain.entity.Task


@Composable
fun Priority(
    modifier: Modifier = Modifier,
    priorityTask: Task.Priority,
) {

    Row(
        modifier = modifier
            .background(
                when (priorityTask) {
                    Task.Priority.HIGH -> Theme.color.status.pinkAccent
                    Task.Priority.MEDIUM -> Theme.color.status.yellowAccent
                    Task.Priority.LOW -> Theme.color.status.greenAccent
                },
                shape = RoundedCornerShape(100)
            )
            .padding(vertical = 6.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                when (priorityTask) {
                    Task.Priority.HIGH -> com.baghdad.tudee.R.drawable.ic_flag
                    Task.Priority.MEDIUM -> com.baghdad.tudee.R.drawable.ic_alert
                    else -> com.baghdad.tudee.R.drawable.ic_trade_down
                }
            ),
            null,
            tint = Theme.color.textColor.onPrimary
        )
        Text(
            text = when (priorityTask) {
                Task.Priority.HIGH -> "High"
                Task.Priority.MEDIUM -> "Medium"
                else -> "Low"
            },
            style = Theme.typography.label.small,
            color = Theme.color.textColor.onPrimary
        )
    }
}


@Composable
@Preview
private fun PriorityPreview() {
    TudeeTheme {
        Priority(priorityTask = Task.Priority.LOW)
    }
}

