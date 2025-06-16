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


@Composable
fun Priority(
    modifier: Modifier = Modifier,
    priorityTask: PriorityTask,
) {

    Row(
        modifier = modifier
            .background(
                when (priorityTask) {
                    PriorityTask.HIGH -> Theme.color.status.pinkAccent
                    PriorityTask.MEDIUM -> Theme.color.status.yellowAccent
                    PriorityTask.LOW -> Theme.color.status.greenAccent
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
                    PriorityTask.HIGH -> com.baghdad.tudee.R.drawable.ic_flag
                    PriorityTask.MEDIUM -> com.baghdad.tudee.R.drawable.ic_alert
                    PriorityTask.LOW -> com.baghdad.tudee.R.drawable.ic_trade_down
                }
            ),
            null,
            tint = Theme.color.textColor.onPrimary
        )
        Text(
            text = when (priorityTask) {
                PriorityTask.HIGH -> "High"
                PriorityTask.MEDIUM -> "Medium"
                PriorityTask.LOW -> "Low"
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
        Priority(priorityTask = PriorityTask.LOW)
    }
}

enum class PriorityTask {
    HIGH,
    MEDIUM,
    LOW
}