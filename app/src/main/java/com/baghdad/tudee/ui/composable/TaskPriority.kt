package com.baghdad.tudee.ui.composable

import androidx.annotation.DrawableRes
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
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme


@Composable
fun TaskPriority(
    priorityTask: PriorityTask,
    modifier: Modifier = Modifier,
) {
    val priorityProperties = when(priorityTask) {
        PriorityTask.HIGH -> PriorityProperties(
            Theme.color.status.pinkAccent,
            R.drawable.ic_flag,
            "High"
        )
        PriorityTask.MEDIUM -> PriorityProperties(
            Theme.color.status.yellowAccent,
            R.drawable.ic_alert,
            "Medium"
        )
        PriorityTask.LOW -> PriorityProperties(
            Theme.color.status.greenAccent,
            R.drawable.ic_trade_down,
            "Low"
        )
    }

    Row(
        modifier = modifier
            .background(priorityProperties.color,
                shape = RoundedCornerShape(100)
            )
            .padding(vertical = 6.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(priorityProperties.iconRes),
            "" +
                    "Priority of task is ${priorityTask.name}",
            tint = Theme.color.textColor.onPrimary
        )
        Text(
            text = priorityProperties.text,
            style = Theme.typography.label.small,
            color = Theme.color.textColor.onPrimary
        )
    }
}

private data class PriorityProperties(
    val color: androidx.compose.ui.graphics.Color,
    @DrawableRes val iconRes: Int,
    val text: String
)

enum class PriorityTask {
    HIGH,
    MEDIUM,
    LOW
}

@Composable
@Preview
private fun PriorityPreview() {
    TudeeTheme {
        TaskPriority(priorityTask = PriorityTask.LOW)
    }
}

